<template>
  <div>
    <div class="blog-sidebar">
      <aside class="widget widget-search">
        <form class="search-form" action="#" method="post">
          <input type="search" name="s" placeholder="Search..." />
          <button type="submit"><i class="fal fa-search"></i></button>
        </form>
      </aside>
      <aside class="widget widget-categories">
        <h3 class="widget-title">Categories</h3>
        <ul>
          <li><a href="#">산책</a><span></span></li>
          <li><a href="#">조깅</a><span>(15)</span></li>
          <li><a href="#">배드민턴</a><span>(8)</span></li>
          <li><a href="#">탁구</a><span>(13)</span></li>
          <li><a href="#">당구</a><span>(4)</span></li>
          <li><a href="#">2개이상</a><span>(12)</span></li>
        </ul>
      </aside>
      <aside class="widget widget-trend-post">
        <h3 class="widget-title">Popular Posts</h3>
        <div class="popular-post">
          <router-link to="/single-news"
            ><img src="@/assets/images/blog/p1.jpg" alt=""
          /></router-link>
          <h5>
            <router-link to="/single-news"
              >Using creative problem Solving</router-link
            >
          </h5>
          <span>March 10, 2020</span>
        </div>
        <div class="popular-post">
          <router-link to="/single-news"
            ><img src="@/assets/images/blog/p2.jpg" alt=""
          /></router-link>
          <h5>
            <router-link to="/single-news"
              >Fundamentals of UI Design</router-link
            >
          </h5>
          <span>Jan 14, 2020</span>
        </div>
        <div class="popular-post">
          <router-link to="/single-news"
            ><img src="@/assets/images/blog/p3.jpg" alt=""
          /></router-link>
          <h5>
            <router-link to="/single-news"
              >Making music with Other people</router-link
            >
          </h5>
          <span>April 12, 2020</span>
        </div>
        <div class="popular-post">
          <router-link to="/single-news"
            ><img src="@/assets/images/blog/p4.jpg" alt=""
          /></router-link>
          <h5>
            <router-link to="/single-news"
              >Brush strokes energize Trees in paintings</router-link
            >
          </h5>
          <span>July 4, 2020</span>
        </div>
      </aside>
      <aside class="widget">
        <h3 class="widget-title">Popular Tags</h3>
        <div class="tags">
          <a href="#">Bisy LMS</a>
          <a href="#">Design</a>
          <a href="#">General</a>
          <a href="#">Online</a>
          <a href="#">Prevention</a>
          <a href="#">Artist</a>
          <a href="#">Education</a>
          <a href="#">Motivation</a>
          <a href="#">Politico</a>
          <a href="#">Live Cases</a>
        </div>
      </aside>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
</style>