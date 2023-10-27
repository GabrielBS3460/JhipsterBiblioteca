<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.livro.home.createOrEditLabel"
          data-cy="LivroCreateUpdateHeading"
          v-text="$t('jhipsterApp.livro.home.createOrEditLabel')"
        >
          Create or edit a Livro
        </h2>
        <div>
          <div class="form-group" v-if="livro.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="livro.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.livro.titulo')" for="livro-titulo">Titulo</label>
            <input
              type="text"
              class="form-control"
              name="titulo"
              id="livro-titulo"
              data-cy="titulo"
              :class="{ valid: !$v.livro.titulo.$invalid, invalid: $v.livro.titulo.$invalid }"
              v-model="$v.livro.titulo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.livro.autor')" for="livro-autor">Autor</label>
            <input
              type="text"
              class="form-control"
              name="autor"
              id="livro-autor"
              data-cy="autor"
              :class="{ valid: !$v.livro.autor.$invalid, invalid: $v.livro.autor.$invalid }"
              v-model="$v.livro.autor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.livro.dataLancamento')" for="livro-dataLancamento"
              >Data Lancamento</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="livro-dataLancamento"
                  v-model="$v.livro.dataLancamento.$model"
                  name="dataLancamento"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="livro-dataLancamento"
                data-cy="dataLancamento"
                type="text"
                class="form-control"
                name="dataLancamento"
                :class="{ valid: !$v.livro.dataLancamento.$invalid, invalid: $v.livro.dataLancamento.$invalid }"
                v-model="$v.livro.dataLancamento.$model"
              />
            </b-input-group>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.livro.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./livro-update.component.ts"></script>
