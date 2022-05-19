<template>
  <div>
    <h1>카카오맵 테스트</h1>
    <div id="map"></div>
    <button @click="getPlaces()" />
  </div>
</template>

<script>
// import http from "@/util/index";
export default {
  data() {
    return {
      map: null,
      // markerPositions1: [
      //   {
      //     title: "카카오",
      //     latlng: new kakao.maps.LatLng(33.450705, 126.570677),
      //   },
      //   {
      //     title: "생태연못",
      //     latlng: new kakao.maps.LatLng(33.450936, 126.569477),
      //   },
      //   {
      //     title: "텃밭",
      //     latlng: new kakao.maps.LatLng(33.450879, 126.56994),
      //   },
      //   {
      //     title: "근린공원",
      //     latlng: new kakao.maps.LatLng(33.451393, 126.570738),
      //   },
      // ],
      markers: [],
      places: [],
    };
  },
  computed: {
    myplace: function () {
      return this.$store.getters.get_place;
    },
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.2429362, 131.8624647, 16),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);

      this.displayMarkers();
    },
    displayMarkers() {
      //현제 표시되어있는 마커가 있으면 marker에 등록된 map을 없애준다.
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
      //2. 마커 이미지 커스터마이징 하기
      //javascript 영역에서 assets의 정보 가져오기
      const imgSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      const imgSize = new kakao.maps.Size(24, 35);
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

      //3. 마커 표시하기
      // console.log("여기 들어오나");
      // console.log(this.myplace);
      let self = this;
      for (var i = 0; i < this.myplace.length; i++) {
        var address = this.myplace[i];
        var geocoder = new kakao.maps.services.Geocoder();
        console.log("여긴 주소", address);
        geocoder.addressSearch(address, function (result, status) {
          if (status == kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            console.log("여기 들어오나", address);
            const marker = new kakao.maps.Marker({
              map: self.map,
              position: coords,
              // title: address,
              image: markerImage,
            });
            self.markers.push(marker);
            // console.log(marker);
            // var infowindow = new kakao.maps.InfoWindow({});
            // infowindow.open(this.map, marker);

            // // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            self.map.setCenter(coords);
          }
        });
      }
      // console.log(self.markers);
      // const marker = new kakao.maps.Marker({
      //   map: this.map,
      //   position: position.latlng,
      //   title: position.title,
      //   image: markerImage,
      // });
      // this.markers.push(marker);
      // });

      //4. 지도를 이동시켜주기
      // const bounds = positions.reduce(
      //   (bounds, position) => bounds.extend(position.latlng),
      //   new kakao.maps.LatLngBounds()
      // );
      // this.map.setBounds(bounds);
    },
    addTolat() {
      this.markerPositions1 = [];
      //주소리스트를 좌표로 반환해주는 함수!!!!
      var geocoder = new window.kakao.maps.services.Geocoder();
      var callback = function (result, status) {
        //여기서 우리 뿌릴 거기에다가 데이터 교체하자!!!!
        if (status === window.kakao.maps.services.Status.OK) {
          // console.log(result);
          // console.log(result[0].address.x);

          // 변환된 좌표 넣기
          let tmp = {
            title: "test",
            latlng: new window.kakao.maps.LatLng(result[0].address.x, result[0].address.y),
          };
          console.log(tmp);
          this.markerPositions1.push(tmp);
        }
      };
      for (var i = 0; i < this.testGetters.length; i++) {
        geocoder.addressSearch(this.testGetters[i], callback);
      }
      console.log("좌표변환완료!!!", this.markerPositions1);
    },
  },
  mounted() {
    this.$store.dispatch("getPlace");
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      // const APIKEY = "da329a02ea7df9bb610bd1a098b81c10";

      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=3e10ef66a0622fef2601f14a6c0ffb9e&libraries=services`;
      /*global kakao */
      script.addEventListener("load", () => {
        window.kakao.maps.load(this.initMap);
        this.displayMarkers(this.markerPositions1);
      });
      document.head.appendChild(script);
    } else {
      console.log("이미 로딩됨", window.kakao);
      this.initMap();
    }
  },
  computed: {
    testGetters: function () {
      return this.$store.getters.getPlaces;
    },
  },
};
</script>

<style>
#map {
  width: 400px;
  height: 400px;
}
</style>
