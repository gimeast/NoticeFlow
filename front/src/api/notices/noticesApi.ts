import { NOTICES } from '@/api/endpoints.ts';
import { apiClient } from '@/api/client.ts';

interface NoticesRequest {
    page: number;
    size: number;
    isDesc?: boolean;
    includeContent?: boolean;
    categoryId?: number;
    search?: string;
}
export const getNotices = async ({ page, size, isDesc, includeContent, categoryId, search }: NoticesRequest) => {
    try {
        const result = await apiClient(
            `${NOTICES}?page=${page}&size=${size}&sort=${isDesc ? 'createdAt,desc' : 'createdAt,asc'}${includeContent ? `&includeContent=${includeContent}` : ''}${categoryId ? `&categoryId=${categoryId}` : ''}${search ? `&keyword=${search}` : ''}`,
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
