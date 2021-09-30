package dev.pengilly

import com.apurebase.kgraphql.GraphQL
import dev.pengilly.domain.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.pengilly.plugins.*
import io.ktor.application.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        install(GraphQL) {
            playground = true
            schema {
                query("hero") {
                    resolver { episode: Episode ->
                        when (episode) {
                            Episode.NEWHOPE, Episode.JEDI -> r2d2
                            Episode.EMPIRE -> luke
                        }
                    }
                }

                query("heroes") {
                    resolver { -> listOf(luke, r2d2) }
                }

                type<Droid>()
                type<Human>()
                enum<Episode>()
            }
        }
    }.start(wait = true)
}
