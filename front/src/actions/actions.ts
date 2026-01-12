import { apiClient } from '@/api/client.ts';
import { ORGANIZATION_REGISTER } from '@/api/endpoints.ts';
import type { RoleType, StatusType } from '@/types/authTypes.ts';

interface JoinState {
    success?: boolean;
    message?: string;
    data?: {
        id: number;
        email: string;
        name: string;
        profileImage: string;
        role: RoleType;
        status: StatusType;
    };
}
export async function joinToOrgan(_previousState: JoinState, formData: FormData) {
    const organData = {
        type: formData.get('type'),
        organizationName: formData.get('organizationName'),
        name: formData.get('name'),
        contactNumber: formData.get('contactNumber'),
        address: formData.get('address'),
    };

    try {
        const { success, message, data } = await apiClient(ORGANIZATION_REGISTER, {
            headers: {
                'Content-Type': 'application/json',
            },
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify(organData),
        });
        return { success, message, data };
    } catch (error) {
        console.error(error);
        return { success: false, message: '회원가입을 실패하였습니다.' };
    }
}
