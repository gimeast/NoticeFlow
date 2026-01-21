import { NOTICES } from '@/api/endpoints.ts';
import { apiClient } from '@/api/client.ts';
import { getCategories } from '@/api/categories/categoriesApi.ts';

export const getNotices = async (page: number, size: number, includeContent?: boolean, categoryId?: number) => {
    try {
        const result = await apiClient(
            `${NOTICES}?page=${page}&size=${size}${includeContent ? `&includeContent=${includeContent}` : ''}${categoryId ? `&categoryId=${categoryId}` : ''}`,
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

export const getNoticesWithCategories = async () => {
    const [noticeData, categoryData] = await Promise.all([getNotices(0, 4, true), getCategories()]);
    return { notices: noticeData.data, categories: categoryData.data };
};
