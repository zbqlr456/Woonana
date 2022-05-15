<template>
  <div>
    MyWorkoutLog 운동선호도 그래프 위치
    <Pie
      :chart-options="chartOptions"
      :chart-data="chartData"
      :chart-id="chartId"
      :dataset-id-key="datasetIdKey"
      :plugins="plugins"
      :css-classes="cssClasses"
      :styles="styles"
      :width="width"
      :height="height"
    />
    <br />
    횟수 출력 위치
    <br />
    데일리 운동로그
    <br />
    데일리 운동로그 플러그인 위치
    <vuejs-heatmap></vuejs-heatmap>
    <br />
  </div>
</template>

<script>
import http from "../../util/index";
import { Pie } from "vue-chartjs/legacy";
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale } from "chart.js";
ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale);
export default {
  name: "PieChart",
  components: {
    Pie,
  },
  props: {
    chartId: {
      type: String,
      default: "pie-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      chartData: {
        labels: ["조깅", "탁구", "골프", "배드민턴"],
        datasets: [
          {
            backgroundColor: ["#41B883", "#E46651", "#00D8FF", "#DD1B16"],
            data: [40, 20, 80, 10],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  methods: {
    async getExerciseInfo(userId) {
      try {
        let data = localStorage.getItem("vuex");
        let parsedata = JSON.parse(data);
        let token = parsedata.loginStore.jwtToken;

        http.defaults.withCredentials = false;
        http.defaults.headers.common["Authorization"] = "Bearer " + token;
        http.defaults.headers.common["withCredentials"] = false;
        const res = await http.get("api/accounts/myexercise/record/" + userId);
        // let token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoid29vbmFuYSIsImlhdCI6MTY1MjE2NDk4NywiZXhwIjoxNjUyMjUxMzg3fQ.VfPXbqMxqR2Y0rIiBfxSH3byym1lTV7QNrtSosZVteQsLDzXKxAtGY-WiY8ieibO7KkzZz6tmxZlAHaDi0IgMA";

        if (res.status === 200) {
          console.log("운동정보를 가져왔습니다.");
          console.log(res);
        } else {
          console.log("운동정보 에러발생");
        }
      } catch (e) {
        console.log(e);
      }
    },
  },
  mounted() {
    this.getExerciseInfo(7);
  },
};
</script>

<style></style>
