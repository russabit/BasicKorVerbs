package com.example.basickorverbs.data

import com.example.basickorverbs.domain.Example
import com.example.basickorverbs.domain.Meaning
import com.example.basickorverbs.domain.Verb

val testModelData = listOf(
    Verb(
        1, "가다", "가다", "가",
        listOf(
            Meaning(
                "to go",
                "идти",
                listOf(
                    Example(
                        "대화가 산으로 간다.",
                        "The conversation is going nowhere. (literally, “The conversation is going to the mountains.”)",
                        "Разговор ни к чему ни приводит."
                    )
                ),
                2,
                0
            ),
            Meaning(
                "(auxiliary, with 어) Marks a continuous action that either stretches from the present into the future, or involves physical movement away from the speaker",
                "(как дополнительный глагол, через 어 после основного) продолжающееся действие, тянущееся из настоящего в будущее, или буквальное физическое движение в направлении от говорящего",
                listOf(
                    Example(
                        "다 먹어간다.",
                        "I've almost finished my food.",
                        "Я почти всё доел."
                    ),
                    Example(
                        "땅거미가 찾아오고 석양은 또 저물어 간다.",
                        "Dusk has found its way, and the evening sun is setting again [and will continue to do so].",
                        "Сумерки пришли, найдя свой путь, и закатное солнце снова садится (уходя)."
                    )
                ),
                2,
                0
            ),
            Meaning(
                "(of cracks and wrinkles) to appear",
                "появляться (трещинкам, морщинам и т.п.)",
                listOf(
                    Example(
                        "금이 간 (금이 가다) 유리",
                        "glass that has cracked",
                        "треснувшее (расколотое) стекло"
                    ),
                ),
                0, 0
            )
        )
    ),
    Verb(
        2, "오다", "오다", "와",
        listOf(
            Meaning(
                "to come (to move towards the speaker)",
                "приходить (по направлению в сторону говорящего)",
                listOf(
                    Example(
                        "오기를 잘했다.",
                        "made the right choice in coming; glad that one came",
                        "хорошо что пришёл (пришла/пришли)"
                    )
                ),
                1,
                0
            ),
            Meaning(
                "to fall (of rain, snow, etc)",
                "идти (о дожде, снеге, граде и т.п.)",
                listOf(
                    Example(
                        "비가 온다.",
                        "It's raining. (literally, “Rain comes.”)",
                        "Дождь пошёл. (букв. “пришёл”)"
                    )
                ),
                3, // 내리다 ?
                0
            ),
            Meaning(
                "Marks a continuous action that stretches from the past into the present. ",
                "продолжающееся действие, тянущееся из прошлого в настоящее",
                listOf(
                    Example(
                        "오랫동안 인도에 살아왔다.",
                        "He has lived in India for a long time.",
                        "Он уже давным-давно переехал в Индию."
                    )
                ),
                1,
                0
            )
        )
    )
)