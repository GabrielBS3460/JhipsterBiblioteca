<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.estudante.home.createOrEditLabel"
          data-cy="EstudanteCreateUpdateHeading"
          v-text="$t('jhipsterApp.estudante.home.createOrEditLabel')"
        >
          Create or edit a Estudante
        </h2>
        <div>
          <div class="form-group" v-if="estudante.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estudante.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.estudante.primeiroNome')" for="estudante-primeiroNome"
              >Primeiro Nome</label
            >
            <input
              type="text"
              class="form-control"
              name="primeiroNome"
              id="estudante-primeiroNome"
              data-cy="primeiroNome"
              :class="{ valid: !$v.estudante.primeiroNome.$invalid, invalid: $v.estudante.primeiroNome.$invalid }"
              v-model="$v.estudante.primeiroNome.$model"
              required
            />
            <div v-if="$v.estudante.primeiroNome.$anyDirty && $v.estudante.primeiroNome.$invalid">
              <small class="form-text text-danger" v-if="!$v.estudante.primeiroNome.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.estudante.sobrenome')" for="estudante-sobrenome">Sobrenome</label>
            <input
              type="text"
              class="form-control"
              name="sobrenome"
              id="estudante-sobrenome"
              data-cy="sobrenome"
              :class="{ valid: !$v.estudante.sobrenome.$invalid, invalid: $v.estudante.sobrenome.$invalid }"
              v-model="$v.estudante.sobrenome.$model"
              required
            />
            <div v-if="$v.estudante.sobrenome.$anyDirty && $v.estudante.sobrenome.$invalid">
              <small class="form-text text-danger" v-if="!$v.estudante.sobrenome.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.estudante.email')" for="estudante-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="estudante-email"
              data-cy="email"
              :class="{ valid: !$v.estudante.email.$invalid, invalid: $v.estudante.email.$invalid }"
              v-model="$v.estudante.email.$model"
              required
            />
            <div v-if="$v.estudante.email.$anyDirty && $v.estudante.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.estudante.email.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterApp.estudante.idade')" for="estudante-idade">Idade</label>
            <input
              type="number"
              class="form-control"
              name="idade"
              id="estudante-idade"
              data-cy="idade"
              :class="{ valid: !$v.estudante.idade.$invalid, invalid: $v.estudante.idade.$invalid }"
              v-model.number="$v.estudante.idade.$model"
              required
            />
            <div v-if="$v.estudante.idade.$anyDirty && $v.estudante.idade.$invalid">
              <small class="form-text text-danger" v-if="!$v.estudante.idade.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.estudante.idade.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
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
            :disabled="$v.estudante.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estudante-update.component.ts"></script>
