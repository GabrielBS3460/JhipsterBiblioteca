<template>
  <div>
    <b-dropdown-item to="/estudante">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estudante')">Estudante</span>
    </b-dropdown-item>
    <b-dropdown-item to="/livro">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.livro')">Livro</span>
    </b-dropdown-item>
    <b-dropdown-item to="/emprestimo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.emprestimo')">Emprestimo</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
