/**
* Challenge
* Services specification.
*
* OpenAPI spec version: 1.0.0
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package com.example.retobootcamp.services.model


/**
 * 
 * @param id 
 * @param nombre 
 * @param apellido 
 * @param acceso 
 * @param admin 
 */
data class Users (
    val id: String? = null,
    val nombre: String? = null,
    val apellido: String? = null,
    val acceso: Boolean? = null,
    val admin: Boolean? = null
)

