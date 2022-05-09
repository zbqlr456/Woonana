<template>
  <div ref="map"></div>
</template>

<script>
export default {
props: ['options'],
mounted(){
    let kakao = window.kakao;
    console.log(this.$refs.map); //null이오면 안됨

    var container = this.$refs.map; //지도를 담을 영역의 DOM 레퍼런스
//     var options = { //지도를 생성할 때 필요한 기본 옵션
//     center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
//     level: 6 //지도의 레벨(확대, 축소 정도)
// };
    const {center, level} = this.options;
    const mapInstance = new kakao.maps.Map(container, {
        center : new kakao.maps.LatLng(center.lat, center.lng),
        level,
    }); //지도 생성 및 객체 리턴
    console.log(mapInstance);
}
}
</script>

<style>
.kmap{
  width: 100%;
  height: 600px;
}
</style>