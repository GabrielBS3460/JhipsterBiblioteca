<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.emprestimo.home.createOrEditLabel"
          data-cy="EmprestimoCreateUpdateHeading"
          v-text="$t('jhipsterApp.emprestimo.home.createOrEditLabel')"
        >
          Create or edit a Emprestimo
        </h2>
        <div>
          <div class="form-group" v-if="emprestimo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="emprestimo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.emprestimo.dataEmprestimo')" for="emprestimo-dataEmprestimo"
              >Data Emprestimo</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="emprestimo-dataEmprestimo"
                  v-model="$v.emprestimo.dataEmprestimo.$model"
                  name="dataEmprestimo"
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
                id="emprestimo-dataEmprestimo"
                data-cy="dataEmprestimo"
                type="text"
                class="form-control"
                name="dataEmprestimo"
                :class="{ valid: !$v.emprestimo.dataEmprestimo.$invalid, invalid: $v.emprestimo.dataEmprestimo.$invalid }"
                v-model="$v.emprestimo.dataEmprestimo.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.emprestimo.estudante')" for="emprestimo-estudante">Estudante</label>
            <select class="form-control" id="emprestimo-estudante" data-cy="estudante" name="estudante" v-model="emprestimo.estudante">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  emprestimo.estudante && estudanteOption.id === emprestimo.estudante.id ? emprestimo.estudante : estudanteOption
                "
                v-for="estudanteOption in estudantes"
                :key="estudanteOption.id"
              >
                {{ estudanteOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.emprestimo.livro')" for="emprestimo-livro">Livro</label>
            <select class="form-control" id="emprestimo-livro" data-cy="livro" name="livro" v-model="emprestimo.livro">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="emprestimo.livro && livroOption.id === emprestimo.livro.id ? emprestimo.livro : livroOption"
                v-for="livroOption in livros"
                :key="livroOption.id"
              >
                {{ livroOption.id }}
              </option>
            </select>
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
            :disabled="$v.emprestimo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./emprestimo-update.component.ts"></script>
