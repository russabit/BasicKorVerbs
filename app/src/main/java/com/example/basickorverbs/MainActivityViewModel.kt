package com.example.basickorverbs

import androidx.lifecycle.ViewModel
import com.example.basickorverbs.data.VerbRepository
import com.example.basickorverbs.domain.Verb

class MainActivityViewModel: ViewModel() {

    private val verbRepository = VerbRepository()

    val jsonString = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"writing\": \"가다\",\n" +
            "    \"actualPronunciation\": \"가다\",\n" +
            "    \"infinitive\": \"가\",\n" +
            "    \"meanings\": [\n" +
            "      {\n" +
            "        \"engTranslation\": \"to go\",\n" +
            "        \"rusTranslation\": \"идти\",\n" +
            "        \"examples\": [\n" +
            "          {\n" +
            "            \"korean\": \"대화가 산으로 간다.\",\n" +
            "            \"engTranslation\": \"The conversation is going nowhere. (literally, “The conversation is going to the mountains.”)\",\n" +
            "            \"rusTranslation\": \"Разговор ни к чему ни приводит.\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"antonymId\": 2,\n" +
            "        \"synonymId\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"engTranslation\": \"(auxiliary, with 어) Marks a continuous action that either stretches from the present into the future, or involves physical movement away from the speaker\",\n" +
            "        \"rusTranslation\": \"(как дополнительный глагол, через 어 после основного) продолжающееся действие, тянущееся из настоящего в будущее, или буквальное физическое движение в направлении от говорящего\",\n" +
            "        \"examples\": [\n" +
            "          {\n" +
            "            \"korean\": \"다 먹어간다.\",\n" +
            "            \"engTranslation\": \"I've almost finished my food.\",\n" +
            "            \"rusTranslation\": \"Я почти всё доел.\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"korean\": \"땅거미가 찾아오고 석양은 또 저물어 간다.\",\n" +
            "            \"engTranslation\": \"Dusk has found its way, and the evening sun is setting again [and will continue to do so].\",\n" +
            "            \"rusTranslation\": \"Сумерки пришли, найдя свой путь, и закатное солнце снова садится (уходя).\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"antonymId\": 2,\n" +
            "        \"synonymId\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"engTranslation\": \"(of cracks and wrinkles) to appear\",\n" +
            "        \"rusTranslation\": \"появляться (трещинкам, морщинам и т.п.)\",\n" +
            "        \"examples\": [\n" +
            "          {\n" +
            "            \"korean\": \"금이 간 (금이 가다) 유리\",\n" +
            "            \"engTranslation\": \"glass that has cracked\",\n" +
            "            \"rusTranslation\": \"треснувшее (расколотое) стекло\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"antonymId\": 0,\n" +
            "        \"synonymId\": 0\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"writing\": \"오다\",\n" +
            "    \"actualPronunciation\": \"오다\",\n" +
            "    \"infinitive\": \"와\",\n" +
            "    \"meanings\": [\n" +
            "      {\n" +
            "        \"engTranslation\": \"to come (to move towards the speaker)\",\n" +
            "        \"rusTranslation\": \"приходить (по направлению в сторону говорящего)\",\n" +
            "        \"examples\": [\n" +
            "          {\n" +
            "            \"korean\": \"오기를 잘했다.\",\n" +
            "            \"engTranslation\": \"made the right choice in coming; glad that one came\",\n" +
            "            \"rusTranslation\": \"хорошо что пришёл (пришла/пришли)\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"antonymId\": 1,\n" +
            "        \"synonymId\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"engTranslation\": \"to fall (of rain, snow, etc)\",\n" +
            "        \"rusTranslation\": \"идти (о дожде, снеге, граде и т.п.)\",\n" +
            "        \"examples\": [\n" +
            "          {\n" +
            "            \"korean\": \"비가 온다.\",\n" +
            "            \"engTranslation\": \"It's raining. (literally, “Rain comes.”)\",\n" +
            "            \"rusTranslation\": \"Дождь пошёл. (букв. “пришёл”)\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"antonymId\": 3,\n" +
            "        \"synonymId\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"engTranslation\": \"Marks a continuous action that stretches from the past into the present.\",\n" +
            "        \"rusTranslation\": \"продолжающееся действие, тянущееся из прошлого в настоящее\",\n" +
            "        \"examples\": [\n" +
            "          {\n" +
            "            \"korean\": \"오랫동안 인도에 살아왔다.\",\n" +
            "            \"engTranslation\": \"He has lived in India for a long time.\",\n" +
            "            \"rusTranslation\": \"Он уже давным-давно переехал в Индию.\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"antonymId\": 1,\n" +
            "        \"synonymId\": 0\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]\n"
    private fun getVerbListFromJson(jsonString: String): List<Verb> {
        return verbRepository.convertJsonToVerbList(jsonString)
    }

    val verbsList = getVerbListFromJson(jsonString)
}