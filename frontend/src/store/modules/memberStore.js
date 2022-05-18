//이부분 아직 수정중
const memberStore = {
    state: {
        masterCheck: false,
        buttonClicked: false
    },
    getters: {
        check_master(state) {
            return state.masterCheck;
        },
        check_buttonClicked(state) {
            return state.buttonClicked;
        }
    },
    mutations: {
        buttonClick(state, buttonClicked) {
            state.buttonClicked = buttonClicked;
        }
    },
    actions: {

    }
};

export default memberStore;