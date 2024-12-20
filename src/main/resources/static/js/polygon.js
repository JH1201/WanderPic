
var polygons = [];
function init(path) {
    fetch(path)
        .then(function (response) {
            return response.json();
        })
        .then(function (geojson) {
            //console.log(geojson);
            polygons = [];


            geojson.geometries.forEach(function (geometry) {
                // 다중 폴리곤과 폴리곤 처리를 위한 조건 추가
                var paths = [];
                if (geometry.type === 'Polygon') {
                    // 단일 폴리곤 처리
                    paths = geometry.coordinates.map(function (ring) {
                        return ring.map(function (coord) {
                            return new kakao.maps.LatLng(coord[1], coord[0]);
                        });
                    });
                } else if (geometry.type === 'MultiPolygon') {
                    // 멀티폴리곤 처리
                    geometry.coordinates.forEach(function (polygon) {
                        var polygonPath = polygon.map(function (ring) {
                            return ring.map(function (coord) {
                                return new kakao.maps.LatLng(coord[1], coord[0]);
                            });
                        });
                        paths.push(polygonPath); // 멀티폴리곤의 각 폴리곤을 paths 배열에 추가
                    });
                }

                // 폴리곤 생성
                paths.forEach(function (path) {
                    var polygon = new kakao.maps.Polygon({
                        map: map,
                        path: path,
                        strokeWeight: 1,
                        strokeColor: '#004c80',
                        strokeOpacity: 0.8,
                        fillColor: '#fff',
                        fillOpacity: 0.7
                    });


                    // 마우스를 올렸을 때 폴리곤 스타일 변경
                    kakao.maps.event.addListener(polygon, 'mouseover', function () {
                        polygon.setOptions({ fillColor: '#09f' });
                    });

                    // 마우스 아웃 시 폴리곤 스타일 변경
                    kakao.maps.event.addListener(polygon, 'mouseout', function () {
                        polygon.setOptions({ fillColor: '#fff' });
                    });

                    var map_level = map.getLevel();


                    /*
                    // 폴리곤 클릭 시 확대
                    kakao.maps.event.addListener(polygon, 'click', function (mouseEvent) {

                        // 클릭한 위치를 중심으로 확대
                        if (map_level >= 11) {
                            map_level = 10
                            map.setLevel(map_level, { anchor: mouseEvent.latLng, animate: { duration: 300 } });
                            //removePolygons();
                        }

                        else {
                            map_level = 12
                            removePolygons();
                            map.setLevel(map_level, { anchor: mouseEvent.latLng, animate: { duration: 300 } });
                            init("/api/ctprvn_geojson");
                        }
                    });
                    */

                    // 폴리곤 클릭 시 
                    kakao.maps.event.addListener(polygon, 'click', function (mouseEvent) {
                        document.getElementById('fileInput').click();
                    });     
                    
                    polygons.push(polygon);
                });

            });
        })
        .catch(error => console.error('GeoJSON 데이터 로드 실패:', error));
}


function removePolygons() {
    polygons.forEach(function (polygon) {
        polygon.setMap(null); // 폴리곤 지도에서 제거
    });
    polygons = []; // 배열 초기화
    console.log("remove polygon");
}   