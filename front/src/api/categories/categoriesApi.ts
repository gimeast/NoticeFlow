import { apiClient } from '@/api/client.ts';
import { CATEGORIES } from '@/api/endpoints.ts';

export const getCategories = async () => {
    try {
        const result = await apiClient(CATEGORIES, { method: 'GET', credentials: 'include' });
        return result;
    } catch (error) {
        console.error(error);
    }
};
