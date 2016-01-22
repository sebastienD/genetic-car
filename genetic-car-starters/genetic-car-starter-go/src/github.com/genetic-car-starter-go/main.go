package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

const (
	serverUrl = "http://genetic-car.herokuapp.com"
	color     = "RED"
)

var (
	urlEvaluation = fmt.Sprintf("%s/simulation/evaluate/%s", serverUrl, color)
	urlChampion   = fmt.Sprintf("%s/simulation/champions/%s", serverUrl, color)
)

type Car struct {
	Chassi struct {
		Vecteurs []float64 `json:"vecteurs"`
		Densite  float64   `json:"densite"`
	} `json:"chassi"`
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

type Wrapper struct {
	Carscore struct {
		Car   Car     `json:"car"`
		Score float64 `json:"score"`
	} `json:"carScore"`
	Statistic struct {
		Team            string `json:"team"`
		Nbrunsimulation int    `json:"nbRunSimulation"`
	} `json:"statistic"`
}

func main() {

	resp, err := http.Get(serverUrl + "/")
	if err != nil {
		panic(fmt.Sprintf("unable to reach server %s", serverUrl))
	}

	wrapper := &Wrapper{}

	resp, err = http.Get(urlChampion)
	if err != nil {
		panic(fmt.Sprintf("unable to reach server %s", urlChampion))
	}

	body, _ := ioutil.ReadAll(resp.Body)
	defer resp.Body.Close()
	err = json.Unmarshal(body, wrapper)
	if err != nil {
		panic(fmt.Sprintf("bad json format =/ %s %s", err.Error(), body))
	}
	fmt.Println(wrapper)
	fmt.Println()

}
