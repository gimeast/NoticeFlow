import { apiClient } from '@/api/client.ts';
import { DASHBOARD_COUNTS, NOTICES } from '@/api/endpoints.ts';

export const getNotices = async (page: number, size: number, includeContent?: boolean) => {
    try {
        const result = await apiClient(
            `${NOTICES}?page=${page}&size=${size}${includeContent ? `&includeContent=${includeContent}` : ''}`,
            {
                method: 'GET',
                credentials: 'include',
            }
        );

        return result;
    } catch (error) {
        console.error(error);
    }
};

export const getCounts = async () => {
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
