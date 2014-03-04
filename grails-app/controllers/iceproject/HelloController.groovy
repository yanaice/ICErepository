package iceproject

class HelloController {
    //http://localhost:8080/IceProject/hello or hello/index because index is default
    def index() {
           Person p = new Person();
        return [person:p]
    }

    def create(String firstName, String lastName, int age ) {
        Person p = new Person(firstname: firstName, lastname:lastName, age: age)
        p.save()
        forward(action:"index")
    }

    def view(){
        def list = Person.findAll()
        return [allPerson:list]
    }

    def back(){
        render (view:'index.gsp')
    }

}