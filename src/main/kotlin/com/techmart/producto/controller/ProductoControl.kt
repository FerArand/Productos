package com.techmart.producto.controller

import com.techmart.producto.model.Producto
import com.techmart.producto.repository.ProductoRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
class ProductoControl(private val repository: ProductoRepo) {

    //Creación
    @PostMapping
    fun crearProducto(@RequestBody producto: Producto): ResponseEntity<Producto> {
        val nuevoProducto = repository.save(producto)
        return ResponseEntity(nuevoProducto, HttpStatus.CREATED)
    }

    //Consulta
    @GetMapping("/{id}")
    fun ObtenerProducto(@PathVariable id: Long): ResponseEntity<Producto> {
        val productoOpt = repository.findById(id)
        return if (productoOpt.isPresent) {
            ResponseEntity(productoOpt.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Modificación
    @PutMapping("/{id}")
    fun actualizarProducto(
        @PathVariable id: Long,
        @RequestBody productoActualizado: Producto
    ): ResponseEntity<Producto> {
        val productoOpt = repository.findById(id)

        return if (productoOpt.isPresent) {
            val productoExistente = productoOpt.get()
            productoExistente.nombre = productoActualizado.nombre
            productoExistente.precio = productoActualizado.precio
            productoExistente.stock = productoActualizado.stock

            val guardado = repository.save(productoExistente)
            ResponseEntity(guardado, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @DeleteMapping("/{id}")
    fun eliminarProducto(@PathVariable id: Long): ResponseEntity<Void> {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}