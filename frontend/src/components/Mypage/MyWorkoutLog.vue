<template>
  <div>
    MyWorkoutLog 운동선호도 그래프 위치
    <Pie
      :chart-options="chartOptions"
      :chart-data="mychartData"
      :chart-id="chartId"
      :dataset-id-key="datasetIdKey"
      :plugins="plugins"
      :css-classes="cssClasses"
      :styles="styles"
      :width="width"
      :height="height"
    />
    <br />
    횟수 출력 위치 총운동횟수 : {{ mymonthlog.excAll }} 이번달운동횟수 :
    {{ mymonthlog.excMonthCnt }}
    <ul>
      <!-- <li v-for="(element, index) in mymonthlog.monthLogs" :key="index">
        <i class="{{ selectIcons(element.exerciseId) }}" style="font-size: 36px"></i>운동종목 :
        {{ selectIcons(element.exerciseId) }} 운동횟수 : {{ element.exerciseCount }}
      </li> -->
    </ul>
    <br />
    데일리 운동로그
    <br />
    데일리 운동로그 플러그인 위치
    <vuejs-heatmap></vuejs-heatmap>
    <br />
  </div>
</template>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script>
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
      //넣을 차트데이터 예시,,, 서버에서 가져온 데이터로 대체한다.
      chartData: {
        labels: ["VueJs", "EmberJs", "ReactJs", "AngularJs"],
        datasets: [
          {
            backgroundColor: ["#41B883", "#E46651", "#00D8FF", "#DD1B16"],
            data: [4, 2, 8, 1],
          },
        ],
      },
      // chartData: {
      //   labels: [],
      //   datasets: [
      //     {
      //       backgroundColor: [],
      //       data: [],
      //     },
      //   ],
      // },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
      exerciseData: [],
    };
  },
  methods: {
    //ChartData를 가져온 값으로 재설정한다.
    setExercisedata: function () {
      this.chartData.datasets[0].data = [];
      this.chartData.labels = [];
      for (let i = 0; i < this.myinfomation.length; i++) {
        // console.log("formoon work");
        this.chartData.datasets[0].data.push(this.myinfomation[i].exerciseCount);
        this.chartData.labels.push(this.myinfomation[i].exerciseName);
      } // end for
      //최대 4개의 색상중 들어간 갯수만 남기고 지우기
      this.chartData.datasets[0].backgroundColor.splice(
        this.myinfomation.length + 1,
        /*this.chartData.datasets.backgroundColor.length*/ 4 - this.myinfomation.length
      );
      // console.log(this.chartData.datasets[0].data);
      // console.log(this.chartData.labels);
    },
    selectIcons: function (id) {
      if (id == 1) {
        return "fas fa-table-tennis";
      } else if (id == 2) {
        return "fa-solid fa-badminton";
      } else if (id == 3) {
        return "fa-solid fa-baseball";
      } else if (id == 4) {
        return "fa-solid fa-person-running";
      }
    },
  },
  async mounted() {
    let tmp = await this.$store.dispatch("getUserId");
    console.log(tmp);
    await this.$store.dispatch("getUserInfo");
    await this.$store.dispatch("getAllExerciseInfo", tmp.userId); // userid에 해당하는 모든 운동로그 가져옴
    await this.$store.dispatch("getMonthExerciseInfo", tmp.userId); // userid에 해당하는 한달정보 가져옴
    console.log(this.myinfomation);
    console.log(this.mymonthlog);
    this.setExercisedata(); // 차트에 예제값을 서버에 얻어온 값으로 교체
  },
  computed: {
    myinfomation: function () {
      return this.$store.getters.get_all_exercise;
    },
    mymonthlog: function () {
      return this.$store.getters.get_month_Exercise;
    },
    mychartData: function () {
      return this.chartData;
    },
  },
};
</script>

<style></style>
