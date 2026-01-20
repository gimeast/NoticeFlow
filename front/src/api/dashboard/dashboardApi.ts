import { apiClient } from '@/api/client.ts';
import { DASHBOARD_COUNTS } from '@/api/endpoints.ts';
import { getNotices } from '@/api/notices/noticesApi.ts';

const getCounts = async () => {
    try {
        const result = await apiClient(DASHBOARD_COUNTS, {
            method: 'GET',
            credentials: 'include',
        });

        return result;
    } catch (error) {
        console.error(error);
    }
};

export const dashboardStatusData = async () => {
    try {
        const [noticeData, counts] = await Promise.all([getNotices(0, 4), getCounts()]);
        return { noticeList: noticeData.data.content, counts: counts.data };
    } catch (error) {
        console.error(error);
    }
};
