import { create } from 'zustand';
import { combine } from 'zustand/middleware';

interface AuthState {
    isAuth: boolean;
    email: string | null;
    name: string | null;
}

const initialState: AuthState = {
    isAuth: false,
    email: null,
    name: null,
};

const authStore = create(
    combine(initialState, set => ({
        login: (email: string, name: string) => set({ isAuth: true, email, name }),
        logout: () => set(initialState),
    }))
);

export default authStore;
