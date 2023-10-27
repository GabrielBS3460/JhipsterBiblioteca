<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estudante">
        <h2 class="jh-entity-heading" data-cy="estudanteDetailsHeading">
          <span v-text="$t('jhipsterApp.estudante.detail.title')">Estudante</span> {{ estudante.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterApp.estudante.primeiroNome')">Primeiro Nome</span>
          </dt>
          <dd>
            <span>{{ estudante.primeiroNome }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.estudante.sobrenome')">Sobrenome</span>
          </dt>
          <dd>
            <span>{{ estudante.sobrenome }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.estudante.email')">Email</span>
          </dt>
          <dd>
            <span>{{ estudante.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.estudante.idade')">Idade</span>
          </dt>
          <dd>
            <span>{{ estudante.idade }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="estudante.id"
          :to="{ name: 'EstudanteEdit', params: { estudanteId: estudante.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estudante-details.component.ts"></script>
