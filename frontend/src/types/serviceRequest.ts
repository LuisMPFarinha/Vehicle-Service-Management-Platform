export type Priority = 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT';

export type ServiceRequestStatus =
  | 'OPEN'
  | 'IN_PROGRESS'
  | 'WAITING_FOR_PARTS'
  | 'COMPLETED'
  | 'CANCELLED';

export interface ServiceRequest {
  id: string;
  vehicleId: string;
  description: string;
  priority: Priority;
  status: ServiceRequestStatus;
  assignedTechnician?: string;
  createdAt: string;
  completedAt?: string;
}
