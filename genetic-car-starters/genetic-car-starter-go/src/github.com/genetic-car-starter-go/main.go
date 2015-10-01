package main

import (
	"encoding/json"
	"fmt"
	"github.com/genetic-car-starter-go/hello"
	"net/http"
)

const serverUrl = "http://genetic-car.herokuapp.com/"

type chassi struct {
	densite  float32
	vecteurs []float32
}

type wheel struct {
	density float32
	radius  float32
	vertex  int
}

type car struct {
	chassi chassi
	wheel1 wheel
	wheel2 wheel
	score  int
}

func main() {
	fmt.Println(hello.BuildHello())

	// Http call
	resp, err := http.Get(serverUrl + "/")
	if err != nil {
		panic("unable to reach server =/")
	}

	// Json decoder
	var c car
	decoder := json.NewDecoder(resp.Body)
	err = decoder.Decode(&c)
	if err != nil {
		panic("bad json format =/")
	}
	fmt.Println(c)

}
