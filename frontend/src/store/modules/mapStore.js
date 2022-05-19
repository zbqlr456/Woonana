import http from "@/util/index";

const mapStore = {
    state: {
        places: [],
        selectPlace: null,
    },

    mutations: {
        SET_PLACE_LIST(state, places) {
            places.forEach((place) => {
                state.places.push(place);
            });
        },
        SET_SELECTPLACE(state, selectPlace) {
            state.selectPlace = selectPlace;
        },
        CLEAN_PLACE_LIST(state) {
            state.places = [];
        }
    },
    actions: {
        getPlace({ commit }) {
            http.get(`api/main`).then(({ data }) => {
                commit("SET_PLACE_LIST", data.place);
            }).catch((err) => {
                console.log(err);
            });
        },
        getSelectPlace({ commit }, selectPlace) {
            commit("SET_SELECTPLACE", selectPlace);
        }
    }
}
export default mapStore;