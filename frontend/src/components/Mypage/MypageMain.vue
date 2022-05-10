<template>
  <div>
    <h1>mypage main</h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      myinfo: {},
    };
  },
  methods: {
    getUserInfo: function () {
      this.myinfo = this.$store.dispatch("getUserInfo");
    },
  },
  mounted: function () {
    this.getUserInfo();
  },
};
</script>

<style></style>
