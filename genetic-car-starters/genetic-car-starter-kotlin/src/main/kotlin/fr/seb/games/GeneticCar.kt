package fr.seb.games

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import java.security.SecureRandom

class Car(val coords: FloatArray) {
    companion object {
        private val random = SecureRandom()

        private fun next(min: Float, max: Float): Float = random.nextFloat() * (max - min) + min
        private fun nextFrameAxis() = next(0.1F, 1.1F)
        private fun nextFrameDensity() = next(30F, 300F)
        private fun nextWheelRadius() = next(0.2F, 0.5F)
        private fun nextWheelDensity() = next(40F, 100F)
        private fun nextVertex() = random.nextInt(7 + 1).toFloat()

        fun random(): Car {
            val coords = FloatArray(23)
            coords[0] = nextFrameAxis()
            coords[1] = 0f
            coords[2] = nextFrameAxis()
            coords[3] = nextFrameAxis()
            coords[4] = 0f
            coords[5] = nextFrameAxis()
            coords[6] = -nextFrameAxis()
            coords[7] = nextFrameAxis()
            coords[8] = -nextFrameAxis()
            coords[9] = 0f
            coords[10] = -nextFrameAxis()
            coords[11] = -nextFrameAxis()
            coords[12] = 0f
            coords[13] = -nextFrameAxis()
            coords[14] = nextFrameAxis()
            coords[15] = -nextFrameAxis()
            coords[16] = nextFrameDensity()
            coords[17] = nextWheelDensity()
            coords[18] = nextWheelRadius()
            coords[19] = nextVertex()
            coords[20] = nextWheelDensity()
            coords[21] = nextWheelRadius()
            coords[22] = nextVertex()
            return Car(coords)
        }
    }
}

class CarView constructor(val chassis: Frame, val wheel1: Wheel, val wheel2: Wheel) {
    fun toCar(): Car = Car(chassis.vecteurs.plus(chassis.densite)
            .plus(wheel1.radius).plus(wheel1.density).plus(wheel1.vertex.toFloat())
            .plus(wheel2.radius).plus(wheel2.density).plus(wheel2.vertex.toFloat()))

    override fun toString(): String {
        return "CarView{chassis=$chassis, wheel1=$wheel1, wheel2= $wheel2}"
    }

    class Frame(val vecteurs: FloatArray, val densite: Float) {
        override fun toString(): String {
            return "Chassi{vecteurs=$vecteurs, densite=$densite}"
        }
    }
    class Wheel(val density: Float, val radius: Float, val vertex: Int) {
        override fun toString(): String {
            return "Wheel{radius=$radius, density=$density, vertex=$vertex}"
        }
    }

    companion object {
        fun fromCar(car: Car): CarView {
            val chassi = Frame(car.coords.slice(0..15).toFloatArray(), car.coords[16])
            val wheel1 = Wheel(car.coords[17], car.coords[18], car.coords[19].toInt())
            val wheel2 = Wheel(car.coords[20], car.coords[21], car.coords[22].toInt())

            return CarView(chassi, wheel1, wheel2)
        }
    }
}

data class CarViewScore(val car: CarView, val score: Float)

enum class Team { RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE }

@Configuration
class WebConfiguration {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()
}

@ConfigurationProperties(prefix = "genetic.server")
class GeneticServerProperties {
    var host: String = ""
}

@SpringBootApplication
@EnableConfigurationProperties(GeneticServerProperties::class)
class GeneticCar @Autowired constructor(val restTemplate: RestTemplate, properties: GeneticServerProperties): CommandLineRunner {
    private val team = Team.RED
    private val url = "${properties.host}/simulation/evaluate/$team"
    private val logger = LoggerFactory.getLogger(GeneticCar::class.java)

    override fun run(vararg args: String?) {
        val carViews = (1..20).map {_ -> Car.random() }.map { car -> CarView.fromCar(car) }
        val scores = evaluate(carViews)
        val champion = scores.sortedByDescending { it.score }.first()
        logger.info("Mon champion est $champion")
    }

    fun evaluate(cars: List<CarView>): List<CarViewScore> {
        return restTemplate.exchange(url, HttpMethod.POST, HttpEntity(cars), object: ParameterizedTypeReference<List<CarViewScore>>(){}).body
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GeneticCar::class.java, *args)
}
