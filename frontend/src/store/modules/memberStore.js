//이부분 아직 수정중
const memberStore = {
    state: {
        masterCheck: false,
        chatBoardIdList: [
            // 0,1,2,3,4,
        ],
    },
    getters: {
        check_master(state) {
            return state.masterCheck;
        },
        check_buttonClicked(state) {
            return state.buttonClicked;
        },
        check_activeChatBoardList(state) {
            return state.chatBoardIdList;
        },

    },
    mutations: {
        pushChatBoardIdList(state, boardId) {//활성화된 채팅보드아이디목록
            state.chatBoardIdList.push(boardId);
        }
    },
    actions: {

    }
};

export default memberStore;