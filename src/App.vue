<script>
import axios from "axios";

export default {
  data() {
    return {
      tema: "",
      result: null,
      currentQues : "",
      quests : [],
      goodAnswer : [],
      badAnswer : [],
      AcsessRes : false,
      ReadyTest : false
    };
  },

  methods: {
    async sendRequest() {
      try {
        this.AcsessRes = true

        const res = await axios.get("http://localhost:8080/index", {
          params: {
            tema: this.tema, // Передача параметра в запросе
          },
        });
        this.result = res.data.choices[0].message.content;
        console.log(this.result);
        this.quests =  [...this.result.matchAll(/^[^\n]+\?/gm)].map(match => match[0]);
        this.badAnswer = [...this.result.matchAll(/^[^\n]+\?\n([^\n]+)\n([^\n]+)/gm)].map(match => [match[1], match[2]]);
        this.goodAnswer = [...this.result.matchAll(/^[^\n]+\?[^\n]*\n[^\n]+\n[^\n]+\n([^\n]+)/gm)].map(match => match[1]);
        console.log(this.badAnswer);



      } catch (error) {
        console.error("Ошибка при отправке запроса:", error);
      }
      this.result = null
      this.AcsessRes = false
      this.ReadyTest = true

    },
  },
};
</script>

<template>
  <header>
    <div class="search-container">
      <input
          type="text"
          v-model="tema"
          placeholder="Создай тест"
      />
      <button @click="sendRequest" v-if="AcsessRes == false" type="button">Создать</button>
    </div>
    <div class="loading" v-if="this.AcsessRes == true && result == null">
      <img src="./assets/VAyR.gif" alt="">
    </div>

  </header>
  <main>
    <div class="test" v-if="this.ReadyTest == true && this.AcsessRes == false" >
      <div class="quest" >
        {{ quests[0] }}
      </div>
      <div class="answer">
        <div class="form-check">
          <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1" checked>
          <label class="form-check-label" for="exampleRadios1">
            {{ badAnswer[0][0] }}
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="option2">
          <label class="form-check-label" for="exampleRadios2">
            {{ goodAnswer[0] }}
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="option2">
          <label class="form-check-label" for="exampleRadios2">
            {{ badAnswer[0][1] }}
          </label>
        </div>
        
      </div>
    </div>
  </main>
</template>

<style>
.search-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 80%;
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  gap: 20px;
}

.search-container input {
  width: 100%;
  max-width: 500px;
  padding: 10px 15px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-container button {
  width: 100%;
  max-width: 500px;
  padding: 12px 20px;
  font-size: 16px;
  background-color: black;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-container button:active {
  background-color: lightgray;
}
.loading{
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}
.test {
  display: flex;
  flex-direction: column; /* Вопрос и ответы располагаются вертикально */
  align-items: center; /* Центрирование по горизонтали */
  justify-content: flex-start; /* Вопрос начинается сверху */
  font-family: "Source Code Pro", serif;
  font-weight: 700; /* Чуть меньше жирности для лучшей читаемости */
  font-size: 20px; /* Базовый размер текста */
  line-height: 1.6; /* Добавление межстрочного расстояния */
  padding: 20px; /* Отступ внутри блока */
  margin: 50px auto; /* Центрирование блока */
  width: 90%; /* Ограничение ширины */
  max-width: 800px; /* Максимальная ширина */
  background: #f9f9f9; /* Добавление фона */
  border-radius: 8px; /* Сглаживание углов */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Легкая тень для визуального отделения */
}

.quest {
  text-align: center; /* Текст вопроса выравнивается по центру */
  font-size: 32px; /* Увеличенный размер для вопроса */
  font-weight: bold; /* Жирный текст для выделения */
  margin-bottom: 25px; /* Отступ снизу */
}




</style>
