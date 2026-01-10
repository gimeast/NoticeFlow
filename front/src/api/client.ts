import { ApiError } from '@/errors/ApiError.ts';
import { REFRESH } from '@/api/endpoints.ts';

interface Options extends RequestInit {
    method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
}

export const apiClient = async (url: string, options?: Options) => {
    try {
        const res = await fetch(url, options);
        if (!res.ok) throw new ApiError('api 통신중 에러가 발생하였습니다.', res.status);
        return res.json();
    } catch (error) {
        if (error instanceof ApiError) {
            if (error.status === 401) {
                const res = await fetch(REFRESH, {
                    method: 'POST',
                    credentials: 'include',
                });
                if (!res.ok) throw new Error('리프레시 토큰 만료');

                const refetchRes = await fetch(url, options);
                if (!refetchRes.ok) throw new Error('api 통신중 에러가 발생하였습니다.');

                return refetchRes.json();
            }
        }
        console.error(error);
        throw error;
    }
};
