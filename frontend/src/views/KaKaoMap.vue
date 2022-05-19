<template>
  <div>
    <h1>카카오맵 테스트</h1>
    <div id="map"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      map: null,
      markerPositions1: [
        {
          title: "카카오",
          latlng: new kakao.maps.LatLng(33.450705, 126.570677),
        },
        {
          title: "생태연못",
          latlng: new kakao.maps.LatLng(33.450936, 126.569477),
        },
        {
          title: "텃밭",
          latlng: new kakao.maps.LatLng(33.450879, 126.56994),
        },
        {
          title: "근린공원",
          latlng: new kakao.maps.LatLng(33.451393, 126.570738),
        },
      ],
      markers: [],
    };
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = { center: new kakao.maps.LatLng(37.2429362, 131.8624647, 16), level: 5 };
      this.map = new kakao.maps.Map(container, options);
    },
    displayMarkers(positions) {
      //현제 표시되어있는 마커가 있으면 marker에 등록된 map을 없애준다.
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
      //2. 마커 이미지 커스터마이징 하기
      //javascript 영역에서 assets의 정보 가져오기
      const imgSrc = require("@/assets/images/icon/1.png");
      const imgSize = new kakao.maps.Size(24, 35);
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

      //3. 마커 표시하기
      positions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: position.latlng,
          title: position.title,
          image: markerImage,
        });
        this.makers.push(marker);
      });

      //4. 지도를 이동시켜주기
      const bounds = positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );
      this.map.setBounds(bounds);
    },
  },
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      // const APIKEY = "da329a02ea7df9bb610bd1a098b81c10";

      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ff82711103143cf3cb8d60b4dda5d5f6&libraries=services`;
      /*global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
        this.displayMarkers(this.markerPositions1);
      });
      document.head.appendChild(script);
    } else {
      console.log("이미 로딩됨", window.kakao);
      this.initMap();
      this.displayMarkers(this.markerPositions1);
    }
  },
};
</script>

<style>
#map {
  width: 400px;
  height: 400px;
}
</style>
