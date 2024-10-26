package com.example.basickorverbs.data

import com.example.basickorverbs.domain.Example
import com.example.basickorverbs.domain.Meaning
import com.example.basickorverbs.domain.Verb

val testModelDataOriginal = listOf(
    Verb(
        id = 1, writing = "가다", actualPronunciation = "가다", infinitive = "가",
        meanings = listOf(
            Meaning(
                engTranslation = "to go",
                rusTranslation = "идти",
                examples = listOf(
                    Example(
                        korean = "대화가 산으로 간다.",
                        engTranslation = "The conversation is going nowhere. (literally, “The conversation is going to the mountains.”)",
                        rusTranslation = "Разговор ни к чему ни приводит."
                    )
                ),
                antonymId = 2,
                synonymId = 0
            ),
            Meaning(
                engTranslation = "(auxiliary, with 어) Marks a continuous action that either stretches from the present into the future, or involves physical movement away from the speaker",
                rusTranslation = "(как дополнительный глагол, через 어 после основного) продолжающееся действие, тянущееся из настоящего в будущее, или буквальное физическое движение в направлении от говорящего",
                examples = listOf(
                    Example(
                        korean = "다 먹어간다.",
                        engTranslation = "I've almost finished my food.",
                        rusTranslation = "Я почти всё доел."
                    ),
                    Example(
                        korean = "땅거미가 찾아오고 석양은 또 저물어 간다.",
                        engTranslation = "Dusk has found its way, and the evening sun is setting again [and will continue to do so].",
                        rusTranslation = "Сумерки пришли, найдя свой путь, и закатное солнце снова садится (уходя)."
                    )
                ),
                antonymId = 2,
                synonymId = 0
            ),
            Meaning(
                engTranslation = "(of cracks and wrinkles) to appear",
                rusTranslation = "появляться (трещинкам, морщинам и т.п.)",
                examples = listOf(
                    Example(
                        korean = "금이 간 (금이 가다) 유리",
                        engTranslation = "glass that has cracked",
                        rusTranslation = "треснувшее (расколотое) стекло"
                    ),
                ),
                antonymId = 0, synonymId = 0
            )
        )
    ),
    Verb(
        id = 2, writing = "오다", actualPronunciation = "오다", infinitive = "와",
        meanings = listOf(
            Meaning(
                engTranslation = "to come (to move towards the speaker)",
                rusTranslation = "приходить (по направлению в сторону говорящего)",
                examples = listOf(
                    Example(
                        korean = "오기를 잘했다.",
                        engTranslation = "made the right choice in coming; glad that one came",
                        rusTranslation = "хорошо что пришёл (пришла/пришли)"
                    )
                ),
                antonymId = 1,
                synonymId = 0
            ),
            Meaning(
                engTranslation = "to fall (of rain, snow, etc)",
                rusTranslation = "идти (о дожде, снеге, граде и т.п.)",
                examples = listOf(
                    Example(
                        korean = "비가 온다.",
                        engTranslation = "It's raining. (literally, “Rain comes.”)",
                        rusTranslation = "Дождь пошёл. (букв. “пришёл”)"
                    )
                ),
                antonymId = 3, // 내리다 ?
                synonymId = 0
            ),
            Meaning(
                engTranslation = "Marks a continuous action that stretches from the past into the present. ",
                rusTranslation = "продолжающееся действие, тянущееся из прошлого в настоящее",
                examples = listOf(
                    Example(
                        korean = "오랫동안 인도에 살아왔다.",
                        engTranslation = "He has lived in India for a long time.",
                        rusTranslation = "Он уже давным-давно переехал в Индию."
                    )
                ),
                antonymId = 1,
                synonymId = 0
            )
        )
    )
)