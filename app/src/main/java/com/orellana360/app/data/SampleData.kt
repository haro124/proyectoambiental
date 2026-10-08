package com.orellana360.app.data

import com.orellana360.app.model.Attraction
import com.orellana360.app.model.Community
import com.orellana360.app.model.Species

object SampleData {

    /**
     * Texto usado en los campos que todavía no tienen información real.
     * El documento del proyecto de vinculacion no incluye fichas por sitio,
     * así que estos campos deben completarse con el levantamiento en campo.
     */
    private const val PENDIENTE = "Información pendiente de validación en campo."

    val attractions = listOf(
        Attraction(
            id = 1,
            name = "ILA KUCHA",
            description = "Sitio potencial turístico de la provincia de Orellana registrado en el proyecto Orellana 360. " +
                    "Su nombre en kichwa hace referencia a una laguna (kucha). $PENDIENTE",
            category = "Río",
            location = "Provincia de Orellana",
            distance = "Por definir",
            rating = 0.0,
            imageFolder = "ila_kucha",
            // COORDENADAS PROVISIONALES: aproximadas al entorno de Francisco de Orellana (Coca).
            latitude = -0.4625,
            longitude = -76.9875,
            activities = listOf(
                "Actividad 1 (por confirmar): $PENDIENTE",
                "Actividad 2 (por confirmar): $PENDIENTE",
                "Actividad 3 (por confirmar): $PENDIENTE"
            ),
            biodiversity = listOf(
                "Flora asociada: $PENDIENTE",
                "Fauna asociada: $PENDIENTE",
                "Ecosistema y estado de conservación: $PENDIENTE"
            ),
            culture = "Valor cultural y comunidad asociada al sitio. $PENDIENTE",
            howToGet = "Ruta de acceso desde Francisco de Orellana (Coca), tipo de vía, medio de transporte y tiempo estimado. $PENDIENTE",
            services = listOf(
                "Guianza: $PENDIENTE",
                "Alimentación: $PENDIENTE",
                "Señalización y seguridad: $PENDIENTE"
            )
        ),
        Attraction(
            id = 2,
            name = "ISHPINGO PAKCHA",
            description = "Sitio potencial turístico de la provincia de Orellana registrado en el proyecto Orellana 360. " +
                    "Su nombre en kichwa hace referencia a una cascada (pakcha). $PENDIENTE",
            category = "Río",
            location = "Provincia de Orellana",
            distance = "Por definir",
            rating = 0.0,
            imageFolder = "ishpingo_pakcha",
            // COORDENADAS PROVISIONALES
            latitude = -0.4810,
            longitude = -76.9520,
            activities = listOf(
                "Actividad 1 (por confirmar): $PENDIENTE",
                "Actividad 2 (por confirmar): $PENDIENTE",
                "Actividad 3 (por confirmar): $PENDIENTE"
            ),
            biodiversity = listOf(
                "Flora asociada: $PENDIENTE",
                "Fauna asociada: $PENDIENTE",
                "Ecosistema y estado de conservación: $PENDIENTE"
            ),
            culture = "Valor cultural y comunidad asociada al sitio. $PENDIENTE",
            howToGet = "Ruta de acceso desde Francisco de Orellana (Coca), tipo de vía, medio de transporte y tiempo estimado. $PENDIENTE",
            services = listOf(
                "Guianza: $PENDIENTE",
                "Alimentación: $PENDIENTE",
                "Señalización y seguridad: $PENDIENTE"
            )
        ),
        Attraction(
            id = 3,
            name = "Cavernas de Guami",
            description = "Sistema de cavernas identificado como sitio potencial turístico de la provincia de Orellana " +
                    "dentro del proyecto Orellana 360. $PENDIENTE",
            category = "Bosque",
            location = "Provincia de Orellana",
            distance = "Por definir",
            rating = 0.0,
            imageFolder = "cavernas_guami",
            // COORDENADAS PROVISIONALES
            latitude = -0.4400,
            longitude = -77.0250,
            activities = listOf(
                "Actividad 1 (por confirmar): $PENDIENTE",
                "Actividad 2 (por confirmar): $PENDIENTE",
                "Actividad 3 (por confirmar): $PENDIENTE"
            ),
            biodiversity = listOf(
                "Flora asociada: $PENDIENTE",
                "Fauna asociada: $PENDIENTE",
                "Ecosistema y estado de conservación: $PENDIENTE"
            ),
            culture = "Valor cultural y comunidad asociada al sitio. $PENDIENTE",
            howToGet = "Ruta de acceso desde Francisco de Orellana (Coca), tipo de vía, medio de transporte y tiempo estimado. $PENDIENTE",
            services = listOf(
                "Guianza: $PENDIENTE",
                "Alimentación: $PENDIENTE",
                "Señalización y seguridad: $PENDIENTE"
            )
        ),
        Attraction(
            id = 4,
            name = "Complejo turístico El Cristal",
            description = "Complejo turístico registrado como sitio potencial de la provincia de Orellana " +
                    "dentro del proyecto Orellana 360. $PENDIENTE",
            category = "Cultural",
            location = "Provincia de Orellana",
            distance = "Por definir",
            rating = 0.0,
            imageFolder = "complejo_el_cristal",
            // COORDENADAS PROVISIONALES
            latitude = -0.4930,
            longitude = -77.0080,
            activities = listOf(
                "Actividad 1 (por confirmar): $PENDIENTE",
                "Actividad 2 (por confirmar): $PENDIENTE",
                "Actividad 3 (por confirmar): $PENDIENTE"
            ),
            biodiversity = listOf(
                "Flora asociada: $PENDIENTE",
                "Fauna asociada: $PENDIENTE",
                "Ecosistema y estado de conservación: $PENDIENTE"
            ),
            culture = "Valor cultural y comunidad asociada al sitio. $PENDIENTE",
            howToGet = "Ruta de acceso desde Francisco de Orellana (Coca), tipo de vía, medio de transporte y tiempo estimado. $PENDIENTE",
            services = listOf(
                "Guianza: $PENDIENTE",
                "Alimentación: $PENDIENTE",
                "Señalización y seguridad: $PENDIENTE"
            )
        )
    )

    val species = listOf(
        Species(
            id = 1,
            name = "Loro Amazónico",
            scientificName = "Amazona ochrocephala",
            description = "Especie emblemática de la Amazonía ecuatoriana. Se encuentra en bordes de bosque y áreas riparias del cantón Orellana. Conocido por su llamativo plumaje amarillo y verde.",
            status = "Vulnerable",
            habitat = "Bosque tropical",
            sightings = 142,
            altitude = "250 m"
        ),
        Species(
            id = 2,
            name = "Jaguar",
            scientificName = "Panthera onca",
            description = "El felino más grande de América. Depredador tope en los ecosistemas amazónicos de Orellana. Símbolo de poder y equilibrio en la selva.",
            status = "Casi Amenazado",
            habitat = "Bosque denso",
            sightings = 28,
            altitude = "300 m"
        ),
        Species(
            id = 3,
            name = "Ceibo",
            scientificName = "Ceiba pentandra",
            description = "Árbol emblemático de la Amazonía, de hasta 70 metros de altura. Sagrado para las culturas amazónicas. Su copa alberga una gran diversidad de fauna.",
            status = "Protegido",
            habitat = "Bosque húmedo",
            sightings = 210,
            altitude = "200 m"
        )
    )

    val communities = listOf(
        Community(
            id = 1,
            name = "Comunidad Waorani",
            description = "Habitantes ancestrales del Yasuní. Conocidos por su profundo conocimiento del bosque y su resistencia cultural. Su lengua es una de las más distintivas de la Amazonía.",
            location = "Yasuní",
            distance = "48 km",
            tags = listOf("Artesanía", "Gastronomía", "Guianza"),
            activities = listOf("Recorrido por el bosque", "Taller de cestería tradicional", "Cocina ancestral amazónica"),
            shortName = "W"
        ),
        Community(
            id = 2,
            name = "Comunidad Shuar",
            description = "Pueblo indígena conocido por su tradición guerrera y su relación espiritual con la selva. Expertos en tejido, cerámica y el arte de la reducción de cabezas (tsantsa).",
            location = "Vía Auca",
            distance = "35 km",
            tags = listOf("Cerámica", "Medicina", "Tejido"),
            activities = listOf("Ceremonia del guayusa", "Taller de alfarería", "Caminata guiada"),
            shortName = "S"
        ),
        Community(
            id = 3,
            name = "Comunidad Kichwa",
            description = "La comunidad más numerosa de la Amazonía ecuatoriana. Reconocidos por su música ancestral, medicina tradicional con plantas y su variada gastronomía amazónica.",
            location = "Río Napo",
            distance = "12 km",
            tags = listOf("Música", "Medicina", "Gastronomía"),
            activities = listOf("Recorrido en canoa", "Música y danza tradicional", "Charla de medicina ancestral"),
            shortName = "K"
        )
    )
}
