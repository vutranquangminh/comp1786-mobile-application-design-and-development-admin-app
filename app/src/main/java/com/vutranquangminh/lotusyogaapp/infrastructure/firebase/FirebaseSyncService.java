package com.vutranquangminh.lotusyogaapp.infrastructure.firebase;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.vutranquangminh.lotusyogaapp.infrastructure.dao.CourseDao;
import com.vutranquangminh.lotusyogaapp.infrastructure.dao.CustomerDao;
import com.vutranquangminh.lotusyogaapp.infrastructure.dao.TeacherDao;
import com.vutranquangminh.lotusyogaapp.infrastructure.dao.UserTransactionDao;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Course;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.CourseCustomerCrossRef;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Customer;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.UserTransaction;

import java.util.List;
import java.util.concurrent.Executors;

public class FirebaseSyncService {
    private final CourseDao courseDao;
    private final CustomerDao customerDao;
    private final TeacherDao teacherDao;
    private final UserTransactionDao userTransactionDao;
    private final FirebaseFirestore firestore;

    public FirebaseSyncService(UserTransactionDao userTransactionDao, CourseDao courseDao, CustomerDao customerDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.customerDao = customerDao;
        this.teacherDao = teacherDao;
        this.userTransactionDao = userTransactionDao;
        firestore = FirebaseFirestore.getInstance();
    }


    /**
     * Syncs all local data to the cloud in a background thread.
     * This method should be called when the app starts or when the user requests a sync.
     */
    public void syncLocalToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            syncCustomersToCloud();
            syncCoursesToCloud();
            syncTeachersToCloud();
            syncCourseCustomerCrossRefsToCloud();
            syncTransactionsToCloud();
        });
    }

//    public void syncCoursesToCloud() {
//        try {
//            List<Course> courseList = courseDao.getAllCourses();
//            for (Course c : courseList) {
//                firestore.collection("courses")
//                        .document(String.valueOf(c.Id))
//                        .set(c)
//                        .addOnFailureListener(e -> {
//                            // Log the error
//                            Log.e("FirebaseSyncService", "Failed to sync course: " + c.Id, e);
//                        });
//            }
//        } catch (Exception e) {
//            Log.e("FirebaseSyncService", "Error syncing courses to cloud", e);
//        }
//    }

    public void syncCoursesToCloud() {
        try {
            List<Course> localCourses = courseDao.getAllCourses();
            if (localCourses.isEmpty()) {
                Log.w("FirebaseSyncService", "Local courses empty, skipping cloud deletion.");
                return;
            }
            List<String> localIds = new java.util.ArrayList<>();
            for (Course c : localCourses) {
                localIds.add(String.valueOf(c.Id));
                firestore.collection("courses")
                        .document(String.valueOf(c.Id))
                        .set(c)
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync course: " + c.Id, e));
            }

            // Fetch all cloud course IDs
            firestore.collection("courses").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (var document : queryDocumentSnapshots) {
                            String cloudId = document.getId();
                            if (!localIds.contains(cloudId)) {
                                // Delete from cloud if not in local
                                firestore.collection("courses").document(cloudId).delete()
                                        .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud course: " + cloudId))
                                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud course: " + cloudId, e));
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud courses", e));
        } catch (Exception e) {
            Log.e("FirebaseSyncService", "Error syncing courses to cloud", e);
        }
    }

    public void syncCustomersToCloud() {
//        try {
//            List<Customer> customerList = customerDao.getAllCustomers();
//            for (Customer customer : customerList) {
//                firestore.collection("customers")
//                        .document(String.valueOf(customer.Id))
//                        .set(customer)
//                        .addOnFailureListener(e -> {
//                            // Log the error
//                            Log.e("FirebaseSyncService", "Failed to sync customer: " + customer.Id, e);
//                        });
//            }
//        } catch (Exception e) {
//            Log.e("FirebaseSyncService", "Error syncing customers to cloud", e);
//        }
        try {
            List<Customer> localCustomers = customerDao.getAllCustomers();
            List<String> localIds = new java.util.ArrayList<>();
            for (Customer c : localCustomers) {
                localIds.add(String.valueOf(c.Id));
                firestore.collection("customers")
                        .document(String.valueOf(c.Id))
                        .set(c)
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync customer: " + c.Id, e));
            }

            // Fetch all cloud customer IDs
            firestore.collection("customers").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (var document : queryDocumentSnapshots) {
                            String cloudId = document.getId();
                            if (!localIds.contains(cloudId)) {
                                // Delete from cloud if not in local
                                firestore.collection("customers").document(cloudId).delete()
                                        .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud customer: " + cloudId))
                                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud customer: " + cloudId, e));
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud customers", e));
        } catch (Exception e) {
            Log.e("FirebaseSyncService", "Error syncing customers to cloud", e);
        }
    }

    public void syncTeachersToCloud() {
//        try {
//            List<Teacher> teacherList = teacherDao.getAllTeachers();
//            for (Teacher teacher : teacherList) {
//                firestore.collection("teachers")
//                        .document(String.valueOf(teacher.Id))
//                        .set(teacher)
//                        .addOnFailureListener(e -> {
//                            // Log the error
//                            Log.e("FirebaseSyncService", "Failed to sync teacher: " + teacher.Id, e);
//                        });
//            }
//        } catch (Exception e) {
//            Log.e("FirebaseSyncService", "Error syncing teachers to cloud", e);
//        }
        try {
            List<Teacher> localTeachers = teacherDao.getAllTeachers();
            List<String> localIds = new java.util.ArrayList<>();
            for (Teacher t : localTeachers) {
                localIds.add(String.valueOf(t.Id));
                firestore.collection("teachers")
                        .document(String.valueOf(t.Id))
                        .set(t)
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync teacher: " + t.Id, e));
            }

            // Fetch all cloud teacher IDs
            firestore.collection("teachers").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (var document : queryDocumentSnapshots) {
                            String cloudId = document.getId();
                            if (!localIds.contains(cloudId)) {
                                // Delete from cloud if not in local
                                firestore.collection("teachers").document(cloudId).delete()
                                        .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud teacher: " + cloudId))
                                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud teacher: " + cloudId, e));
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud teachers", e));
        } catch (Exception e) {
            Log.e("FirebaseSyncService", "Error syncing teachers to cloud", e);
        }
    }




//    This  method make the document become duplicate in the cloud, so I commented it out
//    public void syncCourseCustomerCrossRefsToCloud() {
//        try {
//            List<CourseCustomerCrossRef> crossRefs = customerDao.getAllCourseCustomerCrossRef();
//            for (CourseCustomerCrossRef ref : crossRefs) {
//                firestore.collection("course_customer_crossrefs")
//                        .add(ref)
//                        .addOnFailureListener(e -> {
//                            // Log the error
//                            Log.e("FirebaseSyncService", "Failed to sync cross-ref: " + ref.toString(), e);
//                        });
//            }
//        } catch (Exception e) {
//            Log.e("FirebaseSyncService", "Error syncing course-customer cross-refs to cloud", e);
//        }
//    }

    public void syncCourseCustomerCrossRefsToCloud() {
        try {
            List<CourseCustomerCrossRef> crossRefs = customerDao.getAllCourseCustomerCrossRef();
            for (CourseCustomerCrossRef ref : crossRefs) {
                String docId = ref.customerId + "_" + ref.courseId;
                firestore.collection("course_customer_crossrefs")
                        .document(docId)
                        .set(ref)
                        .addOnFailureListener(e -> {
                            Log.e("FirebaseSyncService", "Failed to sync cross-ref: " + docId, e);
                        });
            }
        } catch (Exception e) {
            Log.e("FirebaseSyncService", "Error syncing course-customer cross-refs to cloud", e);
        }
    }

    public void syncTransactionsToCloud() {
        try {
            List<UserTransaction> userTransactions = userTransactionDao.getAllTransactions();
            for (UserTransaction userTransaction : userTransactions) {
                firestore.collection("transactions")
                        .document(String.valueOf(userTransaction.Id))
                        .set(userTransaction)
                        .addOnFailureListener(e -> {
                            // Log the error
                            Log.e("FirebaseSyncService", "Failed to sync transaction: " + userTransaction.Id, e);
                        });
            }
        } catch (Exception e) {
            Log.e("FirebaseSyncService", "Error syncing transactions to cloud", e);
        }
    }

    /// <summary>
    /// Syncs all data from the cloud to the local database.
    /// This method should be called when the app starts or when the user requests a sync.
    /// </summary>

    public void syncCloudToLocal() {
        Executors.newSingleThreadExecutor().execute(() -> {
            syncCustomersFromCloud();
            syncCoursesFromCloud();
            syncTeachersFromCloud();
            syncCourseCustomerCrossRefsFromCloud();
            syncTransactionsFromCloud();
        });
    }

    public void syncCoursesFromCloud() {
        firestore.collection("courses").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (var document : queryDocumentSnapshots) {
                        Course course = document.toObject(Course.class);
                        course.Id = Integer.parseInt(document.getId());
                        Executors.newSingleThreadExecutor().execute(() -> {
                            try {
                                courseDao.insertCourse(course);
                            } catch (Exception e) {
                                Log.e("FirebaseSyncService", "Insert course failed: " + course.Id, e);
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Error syncing courses from cloud", e));
    }

    public void syncCustomersFromCloud() {
        firestore.collection("customers").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (var document : queryDocumentSnapshots) {
                        Customer customer = document.toObject(Customer.class);
                        customer.Id = Integer.parseInt(document.getId());
                        Executors.newSingleThreadExecutor().execute(() -> {
                            customerDao.insertCustomer(customer);
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Error syncing customers from cloud", e));
    }

    public void syncTeachersFromCloud() {
        firestore.collection("teachers").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (var document : queryDocumentSnapshots) {
                        Teacher teacher = document.toObject(Teacher.class);
                        teacher.Id = Integer.parseInt(document.getId());
                        Executors.newSingleThreadExecutor().execute(() -> {
                            teacherDao.insertTeacher(teacher);
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Error syncing teachers from cloud", e));
    }

    public void syncCourseCustomerCrossRefsFromCloud() {
        firestore.collection("course_customer_crossrefs").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (var document : queryDocumentSnapshots) {
                        CourseCustomerCrossRef ref = document.toObject(CourseCustomerCrossRef.class);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            customerDao.insertCourseCustomerCrossRef(ref);
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Error syncing course-customer cross-refs from cloud", e));
    }

    public void syncTransactionsFromCloud() {
        firestore.collection("transactions").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (var document : queryDocumentSnapshots) {
                        UserTransaction userTransaction = document.toObject(UserTransaction.class);
                        userTransaction.Id = Integer.parseInt(document.getId());
                        Executors.newSingleThreadExecutor().execute(() -> {
                            userTransactionDao.insertTransaction(userTransaction);
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Error syncing transactions from cloud", e));
    }


}
