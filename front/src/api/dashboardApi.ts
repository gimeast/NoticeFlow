import { apiClient } from '@/api/client.ts';
import { NOTICES } from '@/api/endpoints.ts';

export const getNotices = async (page: number, size: number) => {
    try {
        const result = await apiClient(`${NOTICES}?page=${page}&size=${size}`, {
            method: 'GET',
            credentials: 'include',
        });

        return result;
    } catch (error) {
        console.error(error);
    }
};
