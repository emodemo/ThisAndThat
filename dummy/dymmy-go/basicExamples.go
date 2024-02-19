package main

import (
	"fmt"
	"math"
	"time"
)

func basics() {
	var a = "initial"
	fmt.Println(a)

	var b, c int = 1, 2
	fmt.Println(b, c)

	var d = true
	fmt.Println(d)

	var e int
	fmt.Println(e)

	f := "apple" // var f string = "apple"
	fmt.Println(f)

	const n = 5_000_000_00
	const d2 = 3e20 / n
	fmt.Println(d2)
	fmt.Println(int64(d2))
	fmt.Println(math.Sin(n))

	for i := 0; i <= 5; i++ {
		if i%2 == 0 {
			continue
		}
		fmt.Println(i)
	}
}
func ifelse() {

	if 7%2 == 0 {
		fmt.Println("7 is even")
	} else {
		fmt.Println("7 is odd")
	}

	if 8%4 == 0 {
		fmt.Println("8 is divisible by 4")
	}

	if num := 9; num < 0 {
		fmt.Println(num, "is negative")
	} else if num < 10 {
		fmt.Println(num, "has 1 digit")
	} else {
		fmt.Println(num, "has multiple digits")
	}
}
func switchexample() {
	switch time.Now().Weekday() {
	case time.Saturday, time.Sunday:
		fmt.Println("It's the weekend")
	default:
		fmt.Println("It's a weekday")
	}

	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("It's before noon")
	default:
		fmt.Println("It's after noon")
	}

	whatAmI := func(i interface{}) {
		switch t := i.(type) {
		case bool:
			fmt.Println("I'm a bool")
		case int:
			fmt.Println("I'm an int")
		default:
			fmt.Printf("Don't know type %T\n", t)
		}
	}
	whatAmI(true)
	whatAmI(1)
	whatAmI("hey")
}
func arraysAndSlices() {
	var a [5]int
	fmt.Println("emp:", a)
	a[4] = 100
	fmt.Println("get:", a)

	b := [5]int{1, 2, 3, 4, 5}
	fmt.Println("dcl:", b)
	// slices
	var s []string
	fmt.Println("uninit:", s, s == nil, len(s) == 0)
	s = make([]string, 3) // initialize with some size
	s[0] = "a"
	s[1] = "b"
	s[2] = "c"
	fmt.Println("set:", s)
	// increase size
	s = append(s, "d")
	s = append(s, "e", "f")

	// copy
	c := make([]string, len(s))
	copy(c, s)
	l := s[2:5]
	fmt.Println("cpy:", c)
	fmt.Println("sl1:", l)

}
func maps() {
	m := make(map[string]int)
	m["k1"] = 7
	m["k2"] = 13
	fmt.Println("map:", m)
	v1 := m["k1"]
	v3 := m["k3"]
	fmt.Println("v1:", v1)
	fmt.Println("v3:", v3)
	fmt.Println("len:", len(m))
	delete(m, "k2")
	_, isPresent := m["k2"]
	fmt.Println("prs:", isPresent)

}
func ranges() {
	nums := []int{2, 3, 4}
	sum := 0
	for _, num := range nums {
		sum += num
	}
	fmt.Println("sum:", sum)

	for i, num := range nums {
		if num == 3 {
			fmt.Println("index:", i)
		}
	}

	kvs := map[string]string{"a": "apple", "b": "banana"}
	for k, v := range kvs {
		fmt.Printf("%s -> %s\n", k, v)

	}
}
func plus(a int, b int) int {
	return a + b
}
func plusPlus(a, b, c int) int {
	return a + b + c
}
func multipleReturns() (int, int) {
	return 3, 7 // _, c := multipleReturns()
}
func closures() {
	nextInt := intSeq()
	fmt.Println(nextInt())
	fmt.Println(nextInt())
}
func intSeq() func() int {
	i := 0
	return func() int {
		i++
		return i
	}
}
func pointers() {
	i := 1
	j := 2
	p := &i
	fmt.Println("pointer address:", p) // p has the address of i
	fmt.Println("pointer value:", *p)  // the value on that address
	*p = 21                            // change the value written in the p address, hence change the value of the variable i reading fom that address
	p = &j                             // now p points to the address of j
	*p = 22                            // change the value will change J but not I

	fmt.Println("init J:", j)
	fmt.Println("init I:", i)
	zeroval(i)
	fmt.Println("value I:", i)
	fmt.Println("pointer address I:", &i)
	zeropointer(&i) // pass the memory address of the value
	fmt.Println("pointer I:", i)
	fmt.Println("pointer address I:", &i)
}
func zeroval(x int) {
	x = 0
}
func zeropointer(x *int) { // takes a pointer
	*x = 7
}
func structs() {
	//p1 := newPersonPtr("go6o")
	//p2 := newPersonValue("to6o")
	//fmt.Println("person prt address", &p1)
	//fmt.Println("person prt value", p1)
	//fmt.Println(p1.name, p1.age)
	//fmt.Println(&p1.name, &p1.age)
	//pp2 := &p2
	//fmt.Println(pp2.name, pp2.age)
	//fmt.Println("person value address", &p2)
	//fmt.Println("person value value", p2)

	pr1 := person{"Ivan", 1}
	fmt.Println(pr1)
	mutateValue(pr1) // if age is missing it will be 0
	fmt.Println(pr1)
	mutatePointer(&pr1) // if age is missing it will be nil
	fmt.Println(pr1)
	pr1.mutateAge(2) // pr1 is defined as value, hence age is still 3
	fmt.Println(pr1)
	pr1.mutateName("Osman") // pr1 is defined as pointer, hence name is changed to Osman
	fmt.Println(pr1)

	pr2 := pr1 // it assigns a copy, so changing pr2 will not change pr1. To do so, pr2 := &pr1
	fmt.Println(pr2)
	pr2.name = "Vegan"
	fmt.Println(pr2)
	fmt.Println(pr1)
}

// mutable objects
type person struct {
	name string
	age  int
}

// pass by value
func mutateValue(pr person) {
	pr.name = "Dragan"
	pr.age = 2
}

// pass address
func mutatePointer(pr *person) {
	pr.name = "Petkan"
	pr.age = 3
}
func (pr person) mutateAge(age int) {
	pr.age = age
}
func (pr *person) mutateName(name string) {
	pr.name = name
}
func newPersonPtr(name string) *person {
	p := person{name: name}
	p.age = 42
	return &p
}
func newPersonValue(name string) person {
	p := person{name: name, age: 42}
	return p
}
func embeddings() {
	co := container{base{1}, "some name"}
	fmt.Println(co.base.num)
	fmt.Println(co.num) // reference the base.num

}

type base struct {
	num int
}

func (b base) describe() string {
	return fmt.Sprintf("base with num=%v", b.num)
}

type container struct {
	base
	str string
}

func generics() {
	var mapp = map[int]string{1: "1", 2: "2", 3: "3"}
	fmt.Println("keys", MapKeys(mapp))

	list := ListNode[int]{}
	list.Push(1)
	list.Push(3)
	list.Push(2)
	list.Push(4)
	fmt.Println("list", list.GetAll())
}

// MapKeys
// map[KeyType]ValueType
// K has comparable constraint and V has no contraint
func MapKeys[K comparable, V any](m map[K]V) []K {
	result := make([]K, 0, len(m))
	for r := range m {
		result = append(result, r)
	}
	return result
}

type element[T any] struct {
	next  *element[T]
	value T
}
type ListNode[T any] struct {
	head *element[T]
	tail *element[T]
	// head, tail *element[T]
}

func (list *ListNode[T]) Push(t T) {
	if list.tail == nil {
		list.head = &element[T]{value: t}
		list.tail = list.head
	} else {
		list.tail.next = &element[T]{value: t}
		list.tail = list.tail.next
	}
}

func (list *ListNode[T]) GetAll() []T {
	var elements []T
	for element := list.head; element != nil; element = element.next {
		elements = append(elements, element.value)
	}
	return elements
}
