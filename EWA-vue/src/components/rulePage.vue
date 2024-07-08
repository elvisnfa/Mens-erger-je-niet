<template>
  <div>
    <NavBar></NavBar>
    <div class="screen">
      <router-link to="/Dashboard"><img src="../assets/back.png" class="back-button"></router-link>

      <div class="container">
        <h2>Game rules</h2>
        <div class="row">
          <div class="col-sm">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Zes dobbelen</h1>
            <div>
              <button class="btn btn-dark" @click="showText1 = !showText1">{{ buttonText1 }}</button>
              <p v-if="showText1">1. Wanneer je met je dobbelsteen 6 gooit gaat 1 van je pionnen uit je startplaats naar
                het eerste veldplaats (als niet alle 4 pionnen al op veld liggen)</p>
              <p v-if="showText1">2. Vervolgens mag je nog een keer gooien en is de nieuw in veld gekomen pion de pion
                die je moet verplaatsen </p>
              <p v-if="showText1">3. Gooi je drie keer 6 gaat je verste pion terug maar het startplaats</p>
            </div>
          </div>

          <div class="col-sm">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Aantal spelers</h1>
            <div>
              <button class="btn btn-dark" @click="showText2 = !showText2">{{ buttonText1 }}</button>
              <p v-if="showText2">Het spel speel je met minimaal 2 tot maximaal 4 spelers.</p>
            </div>
          </div>

          <div class="col-sm">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Kleuren van spelers</h1>
            <div>
              <button class="btn btn-dark" @click="showText3 = !showText3">{{ buttonText1 }}</button>
              <p v-if="showText3">1. Elke speler krijgt zijn eigen pion kleur toegewezen.</p>
              <p v-if="showText3">2. De pionnen moeten ook in hun eigen kleurfinish eindigen.</p>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Pionnen slaan</h1>
            <div>
              <button class="btn btn-dark" @click="showText4 = !showText4">{{ buttonText1 }}</button>
              <p v-if="showText4">1. Wanneer je met je dobbelsteen genoeg oogjes gooit om te landen op een veld punt
                waar
                al een andere speler staat, mag je die speler slaan.</p>
              <p v-if="showText4">2. Je kan niet op en plek staan waar al een pion staat.</p>
            </div>
          </div>

          <div class="col-sm">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Geslagen worden</h1>
            <div>
              <button class="btn btn-dark" @click="showText5 = !showText5">{{ buttonText1 }}</button>
              <p v-if="showText5">Wordt je geslagen, gaat je pion weer terug naar zijn startveld.</p>
            </div>
          </div>

          <div class="col-sm">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Plaatsen van pionnen</h1>
            <div>
              <button class="btn btn-dark" @click="showText6 = !showText6">{{ buttonText1 }}</button>
              <p v-if="showText6">1. Je kan je eigen pion plaatsen op lege velden</p>
              <p v-if="showText6">2. Je kan niet een pion plaatsen waar al 1 van je eigen pionnen staan</p>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Eind veld en winconditie</h1>
            <div>
              <button class="btn btn-dark" @click="showText7 = !showText7">{{ buttonText1 }}</button>
              <p v-if="showText7">1. Alle pionen moeten in het eind veld belanden</p>
              <p v-if="showText7">2. Je kan de eindveld in zonder stappen terug, dit houdt in als er 3 plekken overzijn
                en
                je gooit een 5 dat je niet veder loopt dan 3.</p>
            </div>
          </div>

          <div class="col">
            <div>
              <img src="../assets/ludo-board.jpg" class="img-fluid" alt="Responsive image">
            </div>
            <h1>Veld posities</h1>
            <div>
              <button class="btn btn-dark" @click="showText8 = !showText8">{{ buttonText1 }}</button>
              <p v-if="showText8">plaats 1: Dit zijn je start plaatsen</p>
              <p v-if="showText8">plaats 2: Dit is je start veld</p>
              <p v-if="showText8">plaats 3: Dit Zijn je eind velden (plaats matchen met je pionnen kleur)</p>
            </div>
          </div>
        </div>
      </div>
      <div class="background">
      </div>
    </div>
  </div>

  <div class="background">
    <img src="../assets/Ludobackground.png">
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: 'rulePage',
  components: {NavBar},
  data() {
    return {
      showText1: false,
      buttonText1: 'Klik hier om regels te tonen',
      showText2: false,
      showText3: false,
      showText4: false,
      showText5: false,
      showText6: false,
      showText7: false,
      showText8: false,
    }
  },
}
</script>

<style scoped>
.back-button {
  height: 50px;
  width: 50px;
  color: white;
  background-color: white;
  border-radius: 20px;
}

.img-fluid {
  max-height: 110px;
  max-width: 110px;
  border-radius: 5px;
  margin-top: 10px;
}

.col-sm {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 20px;
  border: solid black 1px;
  margin: 10px;
}

.col {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 20px;
  border: solid black 1px;
  margin: 10px;
}

.container {
  background: dodgerblue;
  border-radius: 10px;
  text-align: center;
}

.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1000;
  background-color: rgba(5, 11, 98, 1);
}

.background img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: bottom;
  filter: brightness(0.7);
}

p {
  text-align: start;
  color: white;
}
</style>