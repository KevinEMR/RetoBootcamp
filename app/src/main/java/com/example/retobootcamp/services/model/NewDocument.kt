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
 * @param TipoId 
 * @param Identificacion 
 * @param Nombre 
 * @param Apellido 
 * @param Ciudad 
 * @param Correo 
 * @param TipoAdjunto 
 * @param Adjunto 
 */
data class NewDocument (
    val TipoId: String? = null,
    val Identificacion: String? = null,
    val Nombre: String? = null,
    val Apellido: String? = null,
    val Ciudad: String? = null,
    val Correo: String? = null,
    val TipoAdjunto: String? = null,
    val Adjunto: String? = null
)

