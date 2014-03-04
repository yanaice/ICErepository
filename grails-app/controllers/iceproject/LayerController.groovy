package iceproject

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LayerController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Layer.list(params), model: [layerInstanceCount: Layer.count()]
    }

    def show(Layer layerInstance) {
        respond layerInstance
    }

    def create() {
        respond new Layer(params)
    }

    @Transactional
    def save(Layer layerInstance) {
        if (layerInstance == null) {
            notFound()
            return
        }

        if (layerInstance.hasErrors()) {
            respond layerInstance.errors, view: 'create'
            return
        }

        layerInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'layerInstance.label', default: 'Layer'), layerInstance.id])
                redirect layerInstance
            }
            '*' { respond layerInstance, [status: CREATED] }
        }
    }

    def edit(Layer layerInstance) {
        respond layerInstance
    }

    @Transactional
    def update(Layer layerInstance) {
        if (layerInstance == null) {
            notFound()
            return
        }

        if (layerInstance.hasErrors()) {
            respond layerInstance.errors, view: 'edit'
            return
        }

        layerInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Layer.label', default: 'Layer'), layerInstance.id])
                redirect layerInstance
            }
            '*' { respond layerInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Layer layerInstance) {

        if (layerInstance == null) {
            notFound()
            return
        }

        layerInstance.delete flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Layer.label', default: 'Layer'), layerInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'layerInstance.label', default: 'Layer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
