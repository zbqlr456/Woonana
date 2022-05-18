<template>
  <div>
    <!-- Blog Start -->
    <section class="blogpage-section">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 col-md-7">
            <single-blog />
          </div>
        </div>
      </div>
    </section>
    <!-- Blog End -->
    <footer-home-one />
  </div>
</template>

<script>
import SingleBlog from "./SingleBlog.vue";
import FooterHomeOne from "../HomeOne/FooterHomeOne.vue";
export default {
  components: {
    FooterHomeOne,
    SingleBlog,
  },
};
</script>

<style></style>
