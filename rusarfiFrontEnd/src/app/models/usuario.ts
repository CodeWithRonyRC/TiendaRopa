export interface Usuario {
    id?: number;
    nombreUsuario: string;
    passwordUsuario: string;
    rol: string; // o enum si lo defines
}