import { create } from 'zustand';
import { combine } from 'zustand/middleware';
import type { RoleType } from '@/types/authTypes.ts';

interface AuthState {
    isAuth: boolean;
    email: string | null;
    name: string | null;
    role: RoleType;
}

const initialState: AuthState = {
    isAuth: false,
    email: null,
    name: null,
    role: null,
};

const authStore = create(
    combine(initialState, set => ({
        login: (email: string, name: string, role: RoleType) => set({ isAuth: true, email, name, role }),
        logout: () => set(initialState),
    }))
);

export default authStore;
