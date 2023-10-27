<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="livro">
        <h2 class="jh-entity-heading" data-cy="livroDetailsHeading">
          <span v-text="$t('jhipsterApp.livro.detail.title')">Livro</span> {{ livro.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterApp.livro.titulo')">Titulo</span>
          </dt>
          <dd>
            <span>{{ livro.titulo }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.livro.autor')">Autor</span>
          </dt>
          <dd>
            <span>{{ livro.autor }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.livro.dataLancamento')">Data Lancamento</span>
          </dt>
          <dd>
            <span>{{ livro.dataLancamento }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="livro.id" :to="{ name: 'LivroEdit', params: { livroId: livro.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./livro-details.component.ts"></script>
