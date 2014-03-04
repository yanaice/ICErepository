package iceproject

class Layer {

    String name
    String description
    static constraints = {
        name blank: true, unique: true
        description blank: true, nullable: true
    }
}
