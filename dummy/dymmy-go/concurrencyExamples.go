package main

import (
	"fmt"
	"time"
)

func routines() {
	myFunc("sync")
	go myFunc("async")

	go func(from string) {
		myFunc(from)
	}("anonymous async")

	time.Sleep(time.Second) // ensure go routine ends before the main program, better use waitGroup
	fmt.Println("done")
}
func myFunc(from string) {
	for i := 0; i < 3; i++ {
		fmt.Println(from, ":", i)
	}
}
func channels() {
	messages := make(chan string) // create channel, unbuffered by default
	// (a.k.a. will send msg (chan <-), only when there is a receive (<- chan))
	go func() { messages <- "ping" }() // send value to the channel from a go routine
	msg := <-messages                  // receive the message
	fmt.Println(msg)
	// By default sends and receives block until both the sender and receiver are ready.
	// This property allowed us to wait at the end of our program for the "ping" message
	// without having to use any other synchronization.
	// in fact this could be use as a sync tool:
	// done := make(chan bool, 1); then put a true when goroutine is done and read it in the caller function
	<-messages // another way to block until we receive a notification
}
func bufferedChannels() {
	messages := make(chan string, 2) // will accept only 2 items.
	// If we try to add a 3rd element before consuming the existing 2, or consuming non-existing element => deadlock

	messages <- "buffered1"
	messages <- "buffered2"
	fmt.Println(<-messages)
	messages <- "buffered3"
	fmt.Println(<-messages)
	fmt.Println(<-messages)
}
func channelDirection() {
	pings := make(chan string, 1)
	pongs := make(chan string, 1)
	ping(pings, "passed message")
	pong(pings, pongs)
	fmt.Println(<-pongs)
	close(pings) // closing channel
	close(pongs)
}
func ping(pings chan<- string, message string) {
	// send only channel
	pings <- message
}
func pong(pings <-chan string, pongs chan<- string) {
	// ping receive channel, pong send channel
	message := <-pings
	pongs <- message
}
func nonblockingChannels() {
	messages := make(chan string)
	//signals := make(chan bool)
	//Here’s a non-blocking receive.
	//If a value is available on messages then select will take the <-messages case with that value.
	//If not it will immediately take the default case.

	select {
	case msg := <-messages:
		fmt.Println("received message", msg)
	default:
		fmt.Println("no message received")
	}
}
