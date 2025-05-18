export class Persona {
  id?: number;
  nombre: string = '';
  apellido: string = '';
  documento?: string;
  telefono?: string;
}

export class Rol {
  id?: number;
  nombreRol: string = '';
}

export class Usuario {
  id?: number;
  username: string = '';
  password: string = '';
  persona: Persona = new Persona();
  rol: Rol = new Rol();
  activo: boolean = true;
}