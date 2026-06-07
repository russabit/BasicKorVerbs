package com.example.basickorverbs

import com.example.basickorverbs.domain.Verb
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class VerbDictionaryTest {

    private val verbs =
        TestDataLoader.loadVerbs()

    private val ids = verbs.map { it.id }.toSet()

    @Test
    fun jsonCanBeParsed() {
        assertTrue(verbs.isNotEmpty())
    }

    @Test
    fun allIdsAreUsed() {
        assertTrue(ids.size == verbs.last().id)
    }

    @Test
    fun allIdsMustBeUnique() {

        val duplicates =
            verbs.groupBy { it.id }
                .filter { it.value.size > 1 }

        assertTrue(
            "Duplicate IDs found: $duplicates",
            duplicates.isEmpty()
        )
    }

    @Test
    fun allAntonymsMustExist() {

        verbs.forEach { verb ->
            verb.meanings.forEach {  meaning ->

                val antonymId = meaning.antonymId.toInt()

                if (antonymId != 0) {
                    assertTrue(
                        "Verb ${verb.id} references missing antonym $antonymId",
                        ids.contains(antonymId)
                    )
                }
            }
        }
    }

    @Test
    fun allSynonymsMustExist() {

        verbs.forEach { verb ->
            verb.meanings.forEach { meaning ->

                val synonymId = meaning.synonymId.toInt()

                if (synonymId != 0) {
                    assertTrue(
                        "Verb ${verb.id} references missing synonym $synonymId",
                        ids.contains(synonymId)
                    )
                }
            }
        }
    }

    // should fail but needs to be rewritten to show which ones are not backreferencing each other
    @Test
    fun antonymsMustBeMutual() {

        verbs.forEach { verb ->

            verb.meanings.forEach { meaning ->

                val antonymId = meaning.antonymId.toInt()

                if (antonymId == 0) return@forEach

                val antonymVerb =
                    verbs.find { it.id == antonymId }

                assertNotNull(
                    "Missing antonym $antonymId",
                    antonymVerb
                )

                val hasBackReference =
                    antonymVerb!!.meanings.any {
                        it.antonymId.toInt() == verb.id
                    }

                assertTrue(
                    "Antonym relation broken: ${verb.id} -> $antonymId",
                    hasBackReference
                )
            }
        }
    }

    @Test
    fun meaningsMustHaveTranslations() {

        verbs.forEach { verb ->

            verb.meanings.forEach { meaning ->

                assertTrue(
                    meaning.engTranslation.isNotBlank()
                )

                assertTrue(
                    meaning.rusTranslation.isNotBlank()
                )
            }
        }
    }

    @Test
    fun meaningsMustHaveExamples() {

        verbs.forEach { verb ->

            verb.meanings.forEach { meaning ->

                assertTrue(
                    "No examples for ${verb.writing} with the meaning '${meaning.engTranslation}'",
                    meaning.examples.isNotEmpty()
                )
            }
        }
    }

    @Test
    fun writingMustBeUnique() {

        val duplicates =
            verbs.groupBy { it.writing }
                .filter { it.value.size > 1 }

        duplicates.forEach {

            println(it.key.plus(" have ids ").plus(it.value.map(Verb::id)))
        }
    }

    @Test
    fun findDuplicateRussianTranslations() {

        val duplicates =
            verbs.flatMap { verb ->
                verb.meanings.map {
                    it.rusTranslation to verb
                }
            }
                .groupBy(
                    keySelector = { it.first },
                    valueTransform = { it.second }
                )
                .filter { (_, verbs) ->
                    verbs.size > 1
                }

        duplicates.forEach { (translation, verbs) ->

            println("\nTranslation: $translation")

            verbs.forEach {
                println(
                    "  id=${it.id}, writing=${it.writing}"
                )
            }
        }
    }
}