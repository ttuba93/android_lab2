package com.example.lab2app.model

data class Animal(
    val name: String,
    val taxonomy: Taxonomy,
    val locations: List<String>,
    val lifespan: String,
    val diet: String
)

data class Taxonomy(
    val kingdom: String,
    val phylum: String,
    val class_: String,
    val order: String,
    val family: String
)

data class Characteristics(
    val length_min: String,
    val length_max: String,
    val weight_min: String,
    val weight_max: String,
    val lifespan: String
)
