package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"math/rand"
	"net/http"
	"sort"
	"time"
)

func float64Toi(f float64) int {
	return int(f)
}

func random(max int) int {
	return rand.Intn(max)
}

func randomF(min, max float64) float64 {
	return rand.Float64()*(max-min) + min
}

type Car []float64

func newRandCar() Car {
	c := make([]float64, 23, 23)
	c[0] = randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[1] = 0
	c[2] = randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[3] = randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[4] = 0
	c[5] = randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[6] = -randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[7] = randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[8] = -randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[9] = 0
	c[10] = -randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[11] = -randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[12] = 0
	c[13] = -randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[14] = randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[15] = -randomF(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)
	c[16] = randomF(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY)

	c[17] = randomF(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY)
	c[18] = randomF(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS)
	c[19] = float64(random(VERTEX_MAX_VALUE + 1))

	c[20] = randomF(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY)
	c[21] = randomF(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS)
	c[22] = float64(random(VERTEX_MAX_VALUE + 1))
	return c
}

func (c Car) toCarView() CarView {
	cv := CarView{}
	cv.Chassis.Vecteurs = make([]float64, 16, 16)
	for i := 0; i < 16; i++ {
		cv.Chassis.Vecteurs[i] = c[i]
	}
	cv.Chassis.Density = c[16]

	cv.Wheel1.Density = c[17]
	cv.Wheel1.Radius = c[18]
	cv.Wheel1.Vertex = float64Toi(c[19])

	cv.Wheel2.Density = c[20]
	cv.Wheel2.Radius = c[21]
	cv.Wheel2.Vertex = float64Toi(c[22])

	return cv
}

func (c Car) fromCarView(cv CarView) {
	for i := 0; i < 16; i++ {
		c[i] = cv.Chassis.Vecteurs[i]
	}
	c[16] = cv.Chassis.Density

	c[17] = cv.Wheel1.Density
	c[18] = cv.Wheel1.Radius
	c[19] = float64(cv.Wheel1.Vertex)

	c[20] = cv.Wheel2.Density
	c[21] = cv.Wheel2.Radius
	c[22] = float64(cv.Wheel2.Vertex)
}

type CarView struct {
	Chassis struct {
		Vecteurs []float64 `json:"vecteurs"`
		Density  float64   `json:"densite"`
	} `json:"chassis"`
	Wheel1 struct {
		Radius  float64 `json:"radius"`
		Density float64 `json:"density"`
		Vertex  int     `json:"vertex"`
	} `json:"wheel1"`
	Wheel2 struct {
		Radius  float64 `json:"radius"`
		Density float64 `json:"density"`
		Vertex  int     `json:"vertex"`
	} `json:"wheel2"`
}

type CarScoreView struct {
	CarView CarView `json:"car"`
	Score   float64 `json:"score"`
}

type CarScoreViewByScore []CarScoreView

func (a CarScoreViewByScore) Len() int           { return len(a) }
func (a CarScoreViewByScore) Swap(i, j int)      { a[i], a[j] = a[j], a[i] }
func (a CarScoreViewByScore) Less(i, j int) bool { return a[i].Score < a[j].Score }

const (
	//BASE_URL = "http://genetic-car.herokuapp.com"
	BASE_URL = "http://localhost:8080"

	CHASSIS_MIN_AXIS    float64 = 0.1
	CHASSIS_MAX_AXIS    float64 = 1.1
	CHASSIS_MIN_DENSITY float64 = 30
	CHASSIS_MAX_DENSITY float64 = 300
	WHEEL_MIN_RADIUS    float64 = 0.2
	WHEEL_MAX_RADIUS    float64 = 0.5
	WHEEL_MIN_DENSITY   float64 = 40
	WHEEL_MAX_DENSITY   float64 = 100
	VERTEX_MAX_VALUE    int     = 7

	RED    string = "RED"
	YELLOW string = "YELLOW"
	BLUE   string = "BLUE"
	GREEN  string = "GREEN"
	ORANGE string = "ORANGE"
	PURPLE string = "PURPLE"
)

var (
	color         = BLUE
	urlEvaluation = fmt.Sprintf("%s/simulation/evaluate/%s", BASE_URL, color)
	urlChampion   = fmt.Sprintf("%s/simulation/champions/%s", BASE_URL, color)
)

func main() {

	rand.Seed(time.Now().UTC().UnixNano())

	// initialisation
	carViews := make([]CarView, 20, 20)
	for i := range carViews {
		carViews[i] = newRandCar().toCarView()
	}

	// évaluation
	cv, _ := json.Marshal(carViews)

	resp, err := http.Post(urlEvaluation, "application/json", bytes.NewBuffer(cv))
	if err != nil {
		log.Fatalf("unable to reach server %s", urlEvaluation)
	}
	defer resp.Body.Close()

	body, _ := ioutil.ReadAll(resp.Body)
	carScoreViews := []CarScoreView{}
	err = json.Unmarshal(body, &carScoreViews)
	if err != nil {
		log.Fatalf("Bad json format =/ %s %s", err.Error(), body)
	}

	// selection

	// croisement

	// mutation

	sort.Sort(CarScoreViewByScore(carScoreViews))

	log.Printf("Mon champion a un score de %d\n", int(carScoreViews[len(carScoreViews)-1].Score))
}
