import { getCounts, getNotices } from '@/api/dashboardApi.ts';

export const dashboardStatusData = async () => {
    try {
        const [noticeData, counts] = await Promise.all([getNotices(0, 4), getCounts()]);
        return { noticeList: noticeData.data.content, counts: counts.data };
    } catch (error) {
        console.error(error);
    }
};
