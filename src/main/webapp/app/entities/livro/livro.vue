<template>
  <div>
    <h2 id="page-heading" data-cy="LivroHeading">
      <span v-text="$t('jhipsterApp.livro.home.title')" id="livro-heading">Livros</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterApp.livro.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LivroCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-livro"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterApp.livro.home.createLabel')"> Create a new Livro </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && livros && livros.length === 0">
      <span v-text="$t('jhipsterApp.livro.home.notFound')">No livros found</span>
    </div>
    <div class="table-responsive" v-if="livros && livros.length > 0">
      <table class="table table-striped" aria-describedby="livros">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('titulo')">
              <span v-text="$t('jhipsterApp.livro.titulo')">Titulo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'titulo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('autor')">
              <span v-text="$t('jhipsterApp.livro.autor')">Autor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'autor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataLancamento')">
              <span v-text="$t('jhipsterApp.livro.dataLancamento')">Data Lancamento</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataLancamento'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="livro in livros" :key="livro.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LivroView', params: { livroId: livro.id } }">{{ livro.id }}</router-link>
            </td>
            <td>{{ livro.titulo }}</td>
            <td>{{ livro.autor }}</td>
            <td>{{ livro.dataLancamento }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LivroView', params: { livroId: livro.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LivroEdit', params: { livroId: livro.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(livro)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="jhipsterApp.livro.delete.question" data-cy="livroDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-livro-heading" v-text="$t('jhipsterApp.livro.delete.question', { id: removeId })">
          Are you sure you want to delete this Livro?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-livro"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLivro()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="livros && livros.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./livro.component.ts"></script>
