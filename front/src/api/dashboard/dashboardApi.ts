import { getNotices } from '@/api/dashboardApi.ts';

//todo: 더미데이터
function getAmounts() {
    return [1, 1, 3, 8];
}

export const dashboardStatusData = async () => {
    try {
        const [noticeData, amounts] = await Promise.all([getNotices(0, 4), getAmounts()]);
        return { noticeList: noticeData.data.content, amounts };
    } catch (error) {
        console.error(error);
    }
};
